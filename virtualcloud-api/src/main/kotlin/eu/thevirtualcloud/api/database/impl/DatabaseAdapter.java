/*
 * MIT License
 *
 * Copyright (c) 2022 REPLACE_WITH_NAME
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package eu.thevirtualcloud.api.database.impl;

import eu.thevirtualcloud.api.database.IDatabaseAdapter;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseAdapter implements IDatabaseAdapter {

    private final String hostname;
    private final String database;
    private final String username;
    private final String password;

    private final ExecutorService executorService;

    private Connection connection;

    public DatabaseAdapter(String hostname, String database, String username, String password) {
        this.hostname = hostname;
        this.database = database;
        this.username = username;
        this.password = password;
        this.executorService = Executors.newCachedThreadPool();

        try {
            this.connection = DriverManager.getConnection("jbdc:mysql://" + hostname + ":3306" + "/" + database + "?autoReconnect=true", username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(DatabaseAdapter.class.getName()).log(Level.SEVERE, "DatabaseAdapter couldn't connect to database!");
        }
    }

    private boolean isConnected() {
        return this.connection != null;
    }

    @Override
    public void updateQuery(String query) {
        executorService.execute(() -> {
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    @Override
    public String getStringFromTable(String table, String queryColumn, String queryKey, String neededColumn) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + queryColumn + "='" + queryKey + "';");
            resultSet = preparedStatement.executeQuery();
            return resultSet.getString(neededColumn);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "";
        }
    }

    @Override
    public Boolean getBooleanFromTable(String table, String queryColumn, String queryKey, String neededColumn) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + queryColumn + "='" + queryKey + "';");
            resultSet = preparedStatement.executeQuery();
            return resultSet.getBoolean(neededColumn);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    @Override
    public int getIntFromTable(String table, String queryColumn, String queryKey, String neededColumn) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + queryColumn + "='" + queryKey + "';");
            resultSet = preparedStatement.executeQuery();
            return resultSet.getInt(neededColumn);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return 0;
        }
    }

    @Override
    public long getLongFromTable(String table, String queryColumn, String queryKey, String neededColumn) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + queryColumn + "='" + queryKey + "';");
            resultSet = preparedStatement.executeQuery();
            return resultSet.getLong(neededColumn);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return 0;
        }
    }
}
