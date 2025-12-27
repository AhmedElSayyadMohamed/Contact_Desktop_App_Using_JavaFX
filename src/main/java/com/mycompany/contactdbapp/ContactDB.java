package com.mycompany.contactdbapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDB {

    // --- Add Contact ---
    public static int addContact(Contact contact) {
        String sql = "INSERT INTO APP.contacts (national_id, first_name, middle_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, contact.getNational_id());
                ps.setString(2, contact.getFirstName());
                ps.setString(3, contact.getMiddleName());
                ps.setString(4, contact.getLastName());
                ps.setString(5, contact.getEmail());
                ps.setString(6, contact.getPhone());

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            contact.setId(rs.getInt(1));
                        }
                    }
                }

                conn.commit();
                return contact.getId();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            rollbackQuietly(conn);
            return -1;
        } finally {
            closeQuietly(conn);
        }
    }

    // --- Update Contact ---
    public static boolean updateContact(Contact contact) {
        String sql = "UPDATE APP.contacts SET first_name=?, middle_name=?, last_name=?, email=?, phone=?, national_id=? WHERE id=?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, contact.getFirstName());
                ps.setString(2, contact.getMiddleName());
                ps.setString(3, contact.getLastName());
                ps.setString(4, contact.getEmail());
                ps.setString(5, contact.getPhone());
                ps.setString(6, contact.getNational_id());
                ps.setInt(7, contact.getId());

                boolean success = ps.executeUpdate() > 0;
                conn.commit();
                return success;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            rollbackQuietly(conn);
            return false;
        } finally {
            closeQuietly(conn);
        }
    }

    // --- Delete Contact ---
    public static boolean deleteContact(int id) {
        String sql = "DELETE FROM APP.contacts WHERE id=?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                boolean success = ps.executeUpdate() > 0;
                conn.commit();
                return success;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            rollbackQuietly(conn);
            return false;
        } finally {
            closeQuietly(conn);
        }
    }

    // --- Get All Contacts ---
    public static List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT id, national_id, first_name, middle_name, last_name, email, phone FROM APP.contacts";
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Contact contact = new Contact();
                    contact.setId(rs.getInt("id"));
                    contact.setNational_id(rs.getString("national_id"));
                    contact.setFirstName(rs.getString("first_name"));
                    contact.setMiddleName(rs.getString("middle_name"));
                    contact.setLastName(rs.getString("last_name"));
                    contact.setEmail(rs.getString("email"));
                    contact.setPhone(rs.getString("phone"));
                    contacts.add(contact);
                }
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackQuietly(conn);
        } finally {
            closeQuietly(conn);
        }

        return contacts;
    }

    // --- Rollback quietly ---
    private static void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
                System.out.println("Rollback executed due to error.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // --- Close Connection quietly ---
    private static void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
