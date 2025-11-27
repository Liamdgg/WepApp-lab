
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_code VARCHAR(10) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    major VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO students (student_code, full_name, email, major) VALUES
('SV001', 'John Smith', 'john.smith@email.com', 'Computer Science'),
('SV002', 'Emily Johnson', 'emily.j@email.com', 'Information Technology'),
('SV003', 'Michael Brown', 'michael.b@email.com', 'Software Engineering'),
('SV004', 'Sarah Davis', 'sarah.d@email.com', 'Data Science'),
('SV005', 'David Wilson', 'david.w@email.com', 'Computer Science');

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role ENUM('admin', 'user') DEFAULT 'user',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL
);

-- Replace YOUR_HASHED_PASSWORD with the actual hash
INSERT INTO users (username, password, full_name, role) VALUES
('admin', '$2a$10$5vn1jUjWHXiwf4SSWAcMeubxElhih/xEpJZRZvLFqLJT5EEuD2sbu', 'Admin User', 'admin'),
('john', '$2a$10$5vn1jUjWHXiwf4SSWAcMeubxElhih/xEpJZRZvLFqLJT5EEuD2sbu', 'John Doe', 'user'),
('jane', '$2a$10$5vn1jUjWHXiwf4SSWAcMeubxElhih/xEpJZRZvLFqLJT5EEuD2sbu', 'Jane Smith', 'user');









