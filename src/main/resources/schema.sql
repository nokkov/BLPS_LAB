-- ready to view articles

INSERT INTO article (title, author, content, publication_date, is_accepted, views) VALUES
('The Evolution of Electric Cars', 'Alice Johnson', 'Electric cars have come a long way since their inception. This article explores the history, current state, and future of electric vehicles.', '2024-05-29 12:00:00', true, 0),
('Top 10 Sports Cars of 2024', 'Michael Smith', 'In 2024, the sports car market has seen some incredible innovations. Here are the top 10 sports cars you should keep an eye on.', '2024-05-29 13:00:00', true, 0),
('The Future of Autonomous Vehicles', 'Emma Brown', 'Autonomous vehicles are becoming more prevalent on our roads. This article delves into the technology behind them and what we can expect in the coming years.', '2024-05-29 14:00:00', true, 0),
('Classic Cars: A Journey Through Time', 'James Wilson', 'Classic cars hold a special place in automotive history. This article takes a journey through some of the most iconic classic cars and their impact on the industry.', '2024-05-29 15:00:00', true, 0),
('The Rise of Hybrid Vehicles', 'Olivia Martinez', 'Hybrid vehicles offer a blend of traditional combustion engines and electric power. This article examines the rise of hybrid cars and their benefits.', '2024-05-29 16:00:00', true, 0);

-- users (〃￣ω￣〃ゞ

INSERT INTO article_user (username, password, is_moderator, is_manager, is_admin)
VALUES ('admin_user', 'admin_password', false, false, true);

INSERT INTO article_user (username, password, is_moderator, is_manager, is_admin)
VALUES ('manager_user', 'manager_password', false, true, false);

INSERT INTO article_user (username, password, is_moderator, is_manager, is_admin)
VALUES ('moderator_user', 'moderator_password', true, false, false);
