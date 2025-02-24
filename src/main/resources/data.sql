-- Örnek yarışma
INSERT INTO competition (name, description, start_date, end_date, is_active, access_code) 
VALUES ('Java Spring Boot Yarışması', 'Spring Boot ile REST API geliştirme yarışması', 
        CURRENT_TIMESTAMP(), DATEADD('DAY', 7, CURRENT_TIMESTAMP()), true,
        '27e88e716df64b138e73b00e3353dbcf');

-- Örnek sorular
INSERT INTO question (text, points, order_index, competition_id) 
VALUES ('Spring Boot nedir ve hangi avantajları sağlar?', 10, 1, 
        (SELECT id FROM competition WHERE access_code = '27e88e716df64b138e73b00e3353dbcf'));

INSERT INTO question (text, points, order_index, competition_id)
VALUES ('Dependency Injection nedir ve Spring Boot''ta nasıl kullanılır?', 15, 2,
        (SELECT id FROM competition WHERE access_code = '27e88e716df64b138e73b00e3353dbcf'));

-- İlk sorunun şıkları
INSERT INTO option (text, is_correct, order_index, question_id)
VALUES ('Spring Framework üzerine kurulu bir framework''tür', true, 1,
        (SELECT id FROM question WHERE competition_id = (SELECT id FROM competition WHERE access_code = '27e88e716df64b138e73b00e3353dbcf') AND order_index = 1));

INSERT INTO option (text, is_correct, order_index, question_id)
VALUES ('Sadece web uygulamaları için kullanılır', false, 2,
        (SELECT id FROM question WHERE competition_id = (SELECT id FROM competition WHERE access_code = '27e88e716df64b138e73b00e3353dbcf') AND order_index = 1));

INSERT INTO option (text, is_correct, order_index, question_id)
VALUES ('Otomatik yapılandırma özelliği yoktur', false, 3,
        (SELECT id FROM question WHERE competition_id = (SELECT id FROM competition WHERE access_code = '27e88e716df64b138e73b00e3353dbcf') AND order_index = 1));

-- İkinci sorunun şıkları
INSERT INTO option (text, is_correct, order_index, question_id)
VALUES ('Nesneler arası bağımlılıkları yönetme yöntemidir', true, 1,
        (SELECT id FROM question WHERE competition_id = (SELECT id FROM competition WHERE access_code = '27e88e716df64b138e73b00e3353dbcf') AND order_index = 2));

INSERT INTO option (text, is_correct, order_index, question_id)
VALUES ('Sadece veritabanı işlemleri için kullanılır', false, 2,
        (SELECT id FROM question WHERE competition_id = (SELECT id FROM competition WHERE access_code = '27e88e716df64b138e73b00e3353dbcf') AND order_index = 2));

INSERT INTO option (text, is_correct, order_index, question_id)
VALUES ('Spring Boot ile kullanılamaz', false, 3,
        (SELECT id FROM question WHERE competition_id = (SELECT id FROM competition WHERE access_code = '27e88e716df64b138e73b00e3353dbcf') AND order_index = 2)); 