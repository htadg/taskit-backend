INSERT INTO ROLE
(id, is_active, created_at, created_by, updated_at, updated_by, description, name)
VALUES
    (1, 1, '2024-10-01 21:36:42.128295', 'taskit', '2024-10-01 21:36:42.128295', 'taskit', 'Role for SUPER_ADMIN', 'SUPER_ADMIN'),
    (2, 1, '2024-10-01 21:36:42.146292', 'taskit', '2024-10-01 21:36:42.146292', 'taskit', 'Role for BOARD_ADMIN', 'BOARD_ADMIN'),
    (3, 1, '2024-10-01 21:36:42.167295', 'taskit', '2024-10-01 21:36:42.167295', 'taskit', 'Role for USER', 'USER');


INSERT INTO USER
(id, is_active, created_at, created_by, updated_at, updated_by, email, first_name, last_name, password, is_super_admin, username)
VALUES
    (1, 1, '2024-10-01 21:36:41.879328', 'taskit', '2024-10-01 21:36:41.879328', 'taskit', 'superadmin@taskit.com', 'Admin', 'Super', '$2a$10$5.mENnzOtjH9zRvwCa81jOAjgs9B4PdNt2DBLvzW9vqbHO6CVpvqK', 1, 'admin');
