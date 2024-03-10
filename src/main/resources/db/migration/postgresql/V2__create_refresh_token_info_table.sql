-- Create the 'refresh_token_info' table
CREATE TABLE refresh_token_info (
                                    id SERIAL PRIMARY KEY,
                                    token VARCHAR(255) NOT NULL,
                                    user_id INT NOT NULL,
                                    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES "user"(id)
);