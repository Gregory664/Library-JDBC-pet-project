package org.library.repositories;

import org.library.entity.Reader;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.interfaces.ReaderRepository;
import org.library.utils.MySQLConnection;
import org.library.utils.Gender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import static org.library.utils.statements.ReaderSQLStatements.*;

public class ReaderRepositoryImpl implements ReaderRepository {
    private Reader getReaderFromResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt(1);
            String fio = resultSet.getString(2);
            int age = resultSet.getInt(3);
            String address = resultSet.getString(4);
            String phone = resultSet.getString(5);
            String passport = resultSet.getString(6);
            Gender gender = Gender.valueOf(resultSet.getString(7));
            Date date = resultSet.getDate(8);
            return Reader.builder()
                    .id(id)
                    .fio(fio)
                    .age(age)
                    .address(address)
                    .phone(phone)
                    .passport(passport)
                    .gender(gender)
                    .rentBookCopies(new TreeMap<>())
                    .DOB(date)
                    .build();
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public boolean existsByPassport(String passport) {
        boolean isExists = false;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_BY_PASSPORT)) {
            statement.setString(1, passport);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    isExists = resultSet.getInt(1) == 1;
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isExists;
    }

    @Override
    public List<Reader> findAll() {
        List<Reader> readers = new ArrayList<>();

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                readers.add(getReaderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return readers;
    }

    @Override
    public Optional<Reader> findById(Integer id) {
        Reader reader = null;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reader = getReaderFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return Optional.ofNullable(reader);
    }

    @Override
    public boolean existsById(Integer id) {
        boolean isExists = false;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    isExists = resultSet.getInt(1) == 1;
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isExists;
    }

    @Override
    public void deleteAll() {
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ALL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean result;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, id);
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }

    @Override
    public boolean save(Reader reader) {
        boolean isSave;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, reader.getFio());
            statement.setInt(2, reader.getAge());
            statement.setString(3, reader.getAddress());
            statement.setString(4, reader.getPhone());
            statement.setString(5, reader.getPassport());
            statement.setString(6, reader.getGender().name());
            statement.setDate(7, reader.getDOB());

            isSave = statement.executeUpdate() == 1;

            if (isSave) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        reader.setId(resultSet.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isSave;
    }

    @Override
    public long count() {
        long result = 0;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }

    @Override
    public boolean update(Reader reader) {
        boolean result;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, reader.getFio());
            statement.setInt(2, reader.getAge());
            statement.setString(3, reader.getAddress());
            statement.setString(4, reader.getPhone());
            statement.setString(5, reader.getPassport());
            statement.setString(6, reader.getGender().name());
            statement.setDate(7, reader.getDOB());
            statement.setInt(8, reader.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

        return result;
    }

    @Override
    public List<Reader> findByParams(String fio, String phone, String passport) {
        List<String> whereQuery = new ArrayList<>();

        if (!fio.equals("")) {
            whereQuery.add("r.fio like '%" + fio + "%'");
        }

        if (!phone.equals("")) {
            whereQuery.add("r.phone like '%" + phone + "%'");
        }

        if (!passport.equals("")) {
            whereQuery.add("r.passport like '%" + passport + "%'");
        }

        List<Reader> readerList = new ArrayList<>();

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL + " WHERE " + String.join(" AND ", whereQuery));
             ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                readerList.add(getReaderFromResultSet(set));
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return readerList;
    }
}
