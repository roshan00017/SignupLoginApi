-- Create the 'user_role' table
CREATE TABLE user_role (
                           id SERIAL PRIMARY KEY,
                           role_id INT NOT NULL,
                           user_id INT NOT NULL,
                           CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES system_role(id),
                           CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES "user"(id)
);