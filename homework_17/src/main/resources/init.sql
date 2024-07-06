-- Удаляем таблицу answers, если она существует
DROP TABLE IF EXISTS answers;

-- Удаляем таблицу questions, если она существует
DROP TABLE IF EXISTS questions;

-- Удаляем таблицу subjects, если она существует
DROP TABLE IF EXISTS subjects;

-- Создаем таблицу subjects (предметные области)
CREATE TABLE subjects (
                          id SERIAL PRIMARY KEY,     -- Уникальный идентификатор предметной области
                          title VARCHAR(255) NOT NULL -- Название предметной области
);

-- Создаем таблицу questions (вопросы)
CREATE TABLE questions (
                           id SERIAL PRIMARY KEY,     -- Уникальный идентификатор вопроса
                           subject_id INTEGER NOT NULL, -- Внешний ключ к таблице subjects
                           title TEXT NOT NULL,       -- Содержание вопроса
                           FOREIGN KEY (subject_id) REFERENCES subjects(id)
);

-- Создаем таблицу answers (ответы)
CREATE TABLE answers (
                         id SERIAL PRIMARY KEY,     -- Уникальный идентификатор ответа
                         question_id INTEGER NOT NULL, -- Внешний ключ к таблице questions
                         answer TEXT NOT NULL,      -- Содержание ответа
                         is_correct BOOLEAN NOT NULL, -- Булево значение, указывающее правильность ответа
                         FOREIGN KEY (question_id) REFERENCES questions(id)
);

-- Вставка предметной области "Физика"
INSERT INTO subjects (title) VALUES ('Физика');

-- Получение id вставленной предметной области
DO $$
DECLARE
physics_subject_id INTEGER;
BEGIN
SELECT id INTO physics_subject_id FROM subjects WHERE title = 'Физика';

-- Вставка вопросов и ответов для "Физики"
INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Какое ускорение свободного падения на Земле?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '9.8 м/с²', TRUE),
                                                          (currval('questions_id_seq'), '8.9 м/с²', FALSE),
                                                          (currval('questions_id_seq'), '10.1 м/с²', FALSE),
                                                          (currval('questions_id_seq'), '9.2 м/с²', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Кто открыл закон всемирного тяготения?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Альберт Эйнштейн', FALSE),
                                                          (currval('questions_id_seq'), 'Галилео Галилей', FALSE),
                                                          (currval('questions_id_seq'), 'Исаак Ньютон', TRUE),
                                                          (currval('questions_id_seq'), 'Никола Тесла', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Чему равен один джоуль?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '1 Н·м', TRUE),
                                                          (currval('questions_id_seq'), '1 Вт', FALSE),
                                                          (currval('questions_id_seq'), '1 Па', FALSE),
                                                          (currval('questions_id_seq'), '1 В', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Что такое импульс?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Масса', FALSE),
                                                          (currval('questions_id_seq'), 'Произведение массы и скорости', TRUE),
                                                          (currval('questions_id_seq'), 'Сила', FALSE),
                                                          (currval('questions_id_seq'), 'Энергия', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Какой заряд имеет электрон?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Положительный', FALSE),
                                                          (currval('questions_id_seq'), 'Нейтральный', FALSE),
                                                          (currval('questions_id_seq'), 'Отрицательный', TRUE),
                                                          (currval('questions_id_seq'), 'Нулевой', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Как называется прибор для измерения силы?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Термометр', FALSE),
                                                          (currval('questions_id_seq'), 'Амперметр', FALSE),
                                                          (currval('questions_id_seq'), 'Силомер', TRUE),
                                                          (currval('questions_id_seq'), 'Вольтметр', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Что такое световой год?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Год на Солнце', FALSE),
                                                          (currval('questions_id_seq'), 'Расстояние, которое свет проходит за год', TRUE),
                                                          (currval('questions_id_seq'), 'Год на Меркурии', FALSE),
                                                          (currval('questions_id_seq'), 'Год на Земле', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Кто изобрел первый электрический двигатель?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Майкл Фарадей', TRUE),
                                                          (currval('questions_id_seq'), 'Томас Эдисон', FALSE),
                                                          (currval('questions_id_seq'), 'Александр Белл', FALSE),
                                                          (currval('questions_id_seq'), 'Никола Тесла', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Что такое термодинамика?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Наука о звуке', FALSE),
                                                          (currval('questions_id_seq'), 'Наука о тепловых процессах', TRUE),
                                                          (currval('questions_id_seq'), 'Наука о световых процессах', FALSE),
                                                          (currval('questions_id_seq'), 'Наука о механических процессах', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Что изучает квантовая механика?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Большие объекты', FALSE),
                                                          (currval('questions_id_seq'), 'Микроскопические частицы', TRUE),
                                                          (currval('questions_id_seq'), 'Галактики', FALSE),
                                                          (currval('questions_id_seq'), 'Теорию относительности', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Как называется наименьшая часть вещества?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Молекула', FALSE),
                                                          (currval('questions_id_seq'), 'Атом', TRUE),
                                                          (currval('questions_id_seq'), 'Электрон', FALSE),
                                                          (currval('questions_id_seq'), 'Протон', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Что изучает оптика?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Звук', FALSE),
                                                          (currval('questions_id_seq'), 'Свет', TRUE),
                                                          (currval('questions_id_seq'), 'Механические волны', FALSE),
                                                          (currval('questions_id_seq'), 'Тепловые явления', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Чему равен один ампер?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '1 Вт', FALSE),
                                                          (currval('questions_id_seq'), '1 Н·м', FALSE),
                                                          (currval('questions_id_seq'), '1 Кл/с', TRUE),
                                                          (currval('questions_id_seq'), '1 Ом', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Кто открыл фотоэффект?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Альберт Эйнштейн', TRUE),
                                                          (currval('questions_id_seq'), 'Исаак Ньютон', FALSE),
                                                          (currval('questions_id_seq'), 'Галилео Галилей', FALSE),
                                                          (currval('questions_id_seq'), 'Джеймс Клерк Максвелл', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Какое количество энергии называется джоулем?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '1 Вт·с', TRUE),
                                                          (currval('questions_id_seq'), '1 Кл·с', FALSE),
                                                          (currval('questions_id_seq'), '1 Н·м', FALSE),
                                                          (currval('questions_id_seq'), '1 Ом·м', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Чему равен один ньютон?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '1 кг·м/с²', TRUE),
                                                          (currval('questions_id_seq'), '1 Вт·с', FALSE),
                                                          (currval('questions_id_seq'), '1 Кл·с', FALSE),
                                                          (currval('questions_id_seq'), '1 Дж', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Что такое индукция?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Процесс намагничивания', FALSE),
                                                          (currval('questions_id_seq'), 'Процесс создания электрического поля', FALSE),
                                                          (currval('questions_id_seq'), 'Процесс создания электрического тока в замкнутом контуре при изменении магнитного поля', TRUE),
                                                          (currval('questions_id_seq'), 'Процесс создания механического движения', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Какой заряд имеет протон?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Положительный', TRUE),
                                                          (currval('questions_id_seq'), 'Отрицательный', FALSE),
                                                          (currval('questions_id_seq'), 'Нейтральный', FALSE),
                                                          (currval('questions_id_seq'), 'Нулевой', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Что такое период колебаний?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Время одного полного колебания', TRUE),
                                                          (currval('questions_id_seq'), 'Частота колебаний', FALSE),
                                                          (currval('questions_id_seq'), 'Амплитуда колебаний', FALSE),
                                                          (currval('questions_id_seq'), 'Скорость колебаний', FALSE);

INSERT INTO questions (subject_id, title) VALUES (physics_subject_id, 'Чему равен один кулон?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '1 Вт·с', FALSE),
                                                          (currval('questions_id_seq'), '1 А·с', TRUE),
                                                          (currval('questions_id_seq'), '1 Ом·м', FALSE),
                                                          (currval('questions_id_seq'), '1 Н·м', FALSE);
END $$;

-- Вставка предметной области "Математика"
INSERT INTO subjects (title) VALUES ('Математика');

-- Получение id вставленной предметной области
DO $$
DECLARE
math_subject_id INTEGER;
BEGIN
SELECT id INTO math_subject_id FROM subjects WHERE title = 'Математика';

-- Вставка вопросов и ответов для "Математики"
INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равно 2 + 2?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '3', FALSE),
                                                          (currval('questions_id_seq'), '4', TRUE),
                                                          (currval('questions_id_seq'), '5', FALSE),
                                                          (currval('questions_id_seq'), '6', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Сколько градусов в прямом угле?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '45', FALSE),
                                                          (currval('questions_id_seq'), '90', TRUE),
                                                          (currval('questions_id_seq'), '180', FALSE),
                                                          (currval('questions_id_seq'), '360', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равен корень из 16?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '2', FALSE),
                                                          (currval('questions_id_seq'), '4', TRUE),
                                                          (currval('questions_id_seq'), '8', FALSE),
                                                          (currval('questions_id_seq'), '16', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равно 3 * 3?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '6', FALSE),
                                                          (currval('questions_id_seq'), '9', TRUE),
                                                          (currval('questions_id_seq'), '12', FALSE),
                                                          (currval('questions_id_seq'), '15', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равен синус 90 градусов?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '0', FALSE),
                                                          (currval('questions_id_seq'), '0.5', FALSE),
                                                          (currval('questions_id_seq'), '1', TRUE),
                                                          (currval('questions_id_seq'), '0.707', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равно 7 + 8?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '13', FALSE),
                                                          (currval('questions_id_seq'), '14', FALSE),
                                                          (currval('questions_id_seq'), '15', TRUE),
                                                          (currval('questions_id_seq'), '16', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равно 10 / 2?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '4', FALSE),
                                                          (currval('questions_id_seq'), '5', TRUE),
                                                          (currval('questions_id_seq'), '6', FALSE),
                                                          (currval('questions_id_seq'), '7', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Сколько сторон у треугольника?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '2', FALSE),
                                                          (currval('questions_id_seq'), '3', TRUE),
                                                          (currval('questions_id_seq'), '4', FALSE),
                                                          (currval('questions_id_seq'), '5', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равен логарифм 100 по основанию 10?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '1', FALSE),
                                                          (currval('questions_id_seq'), '2', TRUE),
                                                          (currval('questions_id_seq'), '3', FALSE),
                                                          (currval('questions_id_seq'), '4', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равно 12 * 12?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '122', FALSE),
                                                          (currval('questions_id_seq'), '132', FALSE),
                                                          (currval('questions_id_seq'), '144', TRUE),
                                                          (currval('questions_id_seq'), '156', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равно 5 - 3?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '1', FALSE),
                                                          (currval('questions_id_seq'), '2', TRUE),
                                                          (currval('questions_id_seq'), '3', FALSE),
                                                          (currval('questions_id_seq'), '4', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равно 9 / 3?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '1', FALSE),
                                                          (currval('questions_id_seq'), '2', FALSE),
                                                          (currval('questions_id_seq'), '3', TRUE),
                                                          (currval('questions_id_seq'), '4', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Сколько рёбер у куба?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '8', FALSE),
                                                          (currval('questions_id_seq'), '12', TRUE),
                                                          (currval('questions_id_seq'), '6', FALSE),
                                                          (currval('questions_id_seq'), '14', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равно 8 + 12?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '18', FALSE),
                                                          (currval('questions_id_seq'), '20', TRUE),
                                                          (currval('questions_id_seq'), '22', FALSE),
                                                          (currval('questions_id_seq'), '24', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равен 2^3?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '6', FALSE),
                                                          (currval('questions_id_seq'), '8', TRUE),
                                                          (currval('questions_id_seq'), '9', FALSE),
                                                          (currval('questions_id_seq'), '10', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равно 15 - 7?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '6', FALSE),
                                                          (currval('questions_id_seq'), '7', FALSE),
                                                          (currval('questions_id_seq'), '8', TRUE),
                                                          (currval('questions_id_seq'), '9', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Сколько граней у прямоугольного параллелепипеда?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '4', FALSE),
                                                          (currval('questions_id_seq'), '6', TRUE),
                                                          (currval('questions_id_seq'), '8', FALSE),
                                                          (currval('questions_id_seq'), '10', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равен 4 * 5?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '15', FALSE),
                                                          (currval('questions_id_seq'), '20', TRUE),
                                                          (currval('questions_id_seq'), '25', FALSE),
                                                          (currval('questions_id_seq'), '30', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равен квадратный корень из 81?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '7', FALSE),
                                                          (currval('questions_id_seq'), '8', FALSE),
                                                          (currval('questions_id_seq'), '9', TRUE),
                                                          (currval('questions_id_seq'), '10', FALSE);

INSERT INTO questions (subject_id, title) VALUES (math_subject_id, 'Чему равно 3^2?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '6', FALSE),
                                                          (currval('questions_id_seq'), '8', FALSE),
                                                          (currval('questions_id_seq'), '9', TRUE),
                                                          (currval('questions_id_seq'), '10', FALSE);
END $$;

-- Вставка предметной области "Химия"
INSERT INTO subjects (title) VALUES ('Химия');

-- Получение id вставленной предметной области
DO $$
DECLARE
chemistry_subject_id INTEGER;
BEGIN
SELECT id INTO chemistry_subject_id FROM subjects WHERE title = 'Химия';

-- Вставка вопросов и ответов для "Химии"
INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Какой элемент имеет химический символ O?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Кислород', TRUE),
                                                          (currval('questions_id_seq'), 'Водород', FALSE),
                                                          (currval('questions_id_seq'), 'Азот', FALSE),
                                                          (currval('questions_id_seq'), 'Углерод', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Кто открыл периодический закон?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Исаак Ньютон', FALSE),
                                                          (currval('questions_id_seq'), 'Дмитрий Менделеев', TRUE),
                                                          (currval('questions_id_seq'), 'Майкл Фарадей', FALSE),
                                                          (currval('questions_id_seq'), 'Альберт Эйнштейн', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Чему равен молярный объем идеального газа при нормальных условиях?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), '22.4 л', TRUE),
                                                          (currval('questions_id_seq'), '24.5 л', FALSE),
                                                          (currval('questions_id_seq'), '20.0 л', FALSE),
                                                          (currval('questions_id_seq'), '18.2 л', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Какая химическая связь образуется при передаче электронов?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Ковалентная', FALSE),
                                                          (currval('questions_id_seq'), 'Ионная', TRUE),
                                                          (currval('questions_id_seq'), 'Металлическая', FALSE),
                                                          (currval('questions_id_seq'), 'Водородная', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Что такое pH?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Показатель кислотности раствора', TRUE),
                                                          (currval('questions_id_seq'), 'Плотность раствора', FALSE),
                                                          (currval('questions_id_seq'), 'Температура кипения', FALSE),
                                                          (currval('questions_id_seq'), 'Электропроводность', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Что такое изотопы?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Атомы с разным числом протонов', FALSE),
                                                          (currval('questions_id_seq'), 'Атомы с разным числом нейтронов', TRUE),
                                                          (currval('questions_id_seq'), 'Атомы с разным числом электронов', FALSE),
                                                          (currval('questions_id_seq'), 'Атомы с разной массой', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Как называется процесс перехода из жидкого состояния в газообразное?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Конденсация', FALSE),
                                                          (currval('questions_id_seq'), 'Сублимация', FALSE),
                                                          (currval('questions_id_seq'), 'Испарение', TRUE),
                                                          (currval('questions_id_seq'), 'Плавление', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Какой газ выделяется при реакциях кислот с металлами?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Кислород', FALSE),
                                                          (currval('questions_id_seq'), 'Водород', TRUE),
                                                          (currval('questions_id_seq'), 'Углекислый газ', FALSE),
                                                          (currval('questions_id_seq'), 'Азот', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Что такое экзотермическая реакция?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Реакция, сопровождающаяся выделением тепла', TRUE),
                                                          (currval('questions_id_seq'), 'Реакция, сопровождающаяся поглощением тепла', FALSE),
                                                          (currval('questions_id_seq'), 'Реакция, происходящая при высоких температурах', FALSE),
                                                          (currval('questions_id_seq'), 'Реакция, происходящая при низких температурах', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Что такое катализатор?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Вещество, замедляющее реакцию', FALSE),
                                                          (currval('questions_id_seq'), 'Вещество, ускоряющее реакцию', TRUE),
                                                          (currval('questions_id_seq'), 'Вещество, изменяющее равновесие реакции', FALSE),
                                                          (currval('questions_id_seq'), 'Вещество, изменяющее состояние вещества', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Как называется процесс разложения воды на водород и кислород?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Гидролиз', FALSE),
                                                          (currval('questions_id_seq'), 'Электролиз', TRUE),
                                                          (currval('questions_id_seq'), 'Фотолиз', FALSE),
                                                          (currval('questions_id_seq'), 'Термолиз', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Какой элемент имеет химический символ Fe?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Медь', FALSE),
                                                          (currval('questions_id_seq'), 'Железо', TRUE),
                                                          (currval('questions_id_seq'), 'Фтор', FALSE),
                                                          (currval('questions_id_seq'), 'Сера', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Что такое химическая валентность?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Способность атомов соединяться с другими атомами', TRUE),
                                                          (currval('questions_id_seq'), 'Способность атомов проводить электрический ток', FALSE),
                                                          (currval('questions_id_seq'), 'Способность атомов выделять тепло', FALSE),
                                                          (currval('questions_id_seq'), 'Способность атомов поглощать свет', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Как называется процесс образования твёрдого вещества из раствора?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Растворение', FALSE),
                                                          (currval('questions_id_seq'), 'Кристаллизация', TRUE),
                                                          (currval('questions_id_seq'), 'Испарение', FALSE),
                                                          (currval('questions_id_seq'), 'Конденсация', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Как называется процесс соединения атомов с образованием молекул?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Полимеризация', FALSE),
                                                          (currval('questions_id_seq'), 'Синтез', TRUE),
                                                          (currval('questions_id_seq'), 'Гидролиз', FALSE),
                                                          (currval('questions_id_seq'), 'Окисление', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Какой газ является основным компонентом воздуха?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Кислород', FALSE),
                                                          (currval('questions_id_seq'), 'Азот', TRUE),
                                                          (currval('questions_id_seq'), 'Углекислый газ', FALSE),
                                                          (currval('questions_id_seq'), 'Водород', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Что такое молекулярная масса?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Сумма масс всех атомов в молекуле', TRUE),
                                                          (currval('questions_id_seq'), 'Масса одного атома', FALSE),
                                                          (currval('questions_id_seq'), 'Масса одного электрона', FALSE),
                                                          (currval('questions_id_seq'), 'Масса одного протона', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Какая кислота содержится в желудочном соке человека?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Серная кислота', FALSE),
                                                          (currval('questions_id_seq'), 'Соляная кислота', TRUE),
                                                          (currval('questions_id_seq'), 'Уксусная кислота', FALSE),
                                                          (currval('questions_id_seq'), 'Азотная кислота', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Какой элемент является основой органической химии?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Водород', FALSE),
                                                          (currval('questions_id_seq'), 'Кислород', FALSE),
                                                          (currval('questions_id_seq'), 'Углерод', TRUE),
                                                          (currval('questions_id_seq'), 'Азот', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Что такое аллотропия?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Способность элемента существовать в нескольких формах', TRUE),
                                                          (currval('questions_id_seq'), 'Способность атомов соединяться в молекулы', FALSE),
                                                          (currval('questions_id_seq'), 'Способность вещества растворяться в воде', FALSE),
                                                          (currval('questions_id_seq'), 'Способность атомов выделять тепло', FALSE);

INSERT INTO questions (subject_id, title) VALUES (chemistry_subject_id, 'Какая химическая формула у воды?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'H2O', TRUE),
                                                          (currval('questions_id_seq'), 'CO2', FALSE),
                                                          (currval('questions_id_seq'), 'O2', FALSE),
                                                          (currval('questions_id_seq'), 'N2', FALSE);
END $$;


-- Вставка предметной области "Java"
INSERT INTO subjects (title) VALUES ('Java');

-- Получение id вставленной предметной области
DO $$
DECLARE
java_subject_id INTEGER;
BEGIN
SELECT id INTO java_subject_id FROM subjects WHERE title = 'Java';

-- Вставка вопросов и ответов для "Java"
INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое JVM?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Java Visual Machine', FALSE),
                                                          (currval('questions_id_seq'), 'Java Virtual Machine', TRUE),
                                                          (currval('questions_id_seq'), 'Java Verification Machine', FALSE),
                                                          (currval('questions_id_seq'), 'Java Variable Machine', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое JDK?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Java Development Kit', TRUE),
                                                          (currval('questions_id_seq'), 'Java Deployment Kit', FALSE),
                                                          (currval('questions_id_seq'), 'Java Debugging Kit', FALSE),
                                                          (currval('questions_id_seq'), 'Java Dynamic Kit', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое JRE?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Java Runtime Environment', TRUE),
                                                          (currval('questions_id_seq'), 'Java Running Environment', FALSE),
                                                          (currval('questions_id_seq'), 'Java Rendering Environment', FALSE),
                                                          (currval('questions_id_seq'), 'Java Real-time Environment', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое наследование в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Механизм, позволяющий одному классу передавать свои свойства и методы другому классу', TRUE),
                                                          (currval('questions_id_seq'), 'Процесс, при котором один объект копирует данные другого объекта', FALSE),
                                                          (currval('questions_id_seq'), 'Способность метода вызывать другой метод', FALSE),
                                                          (currval('questions_id_seq'), 'Механизм создания экземпляров класса', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое полиморфизм в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Способность объекта принимать несколько форм', TRUE),
                                                          (currval('questions_id_seq'), 'Процесс создания новых объектов', FALSE),
                                                          (currval('questions_id_seq'), 'Механизм объединения классов', FALSE),
                                                          (currval('questions_id_seq'), 'Способность метода менять свой тип', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое инкапсуляция в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Процесс скрытия реализации деталей объекта и предоставления только необходимых методов', TRUE),
                                                          (currval('questions_id_seq'), 'Способность класса наследовать методы другого класса', FALSE),
                                                          (currval('questions_id_seq'), 'Механизм создания новых объектов', FALSE),
                                                          (currval('questions_id_seq'), 'Способность метода вызывать другой метод', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое абстракция в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Процесс выделения общих характеристик объекта и игнорирование частных', TRUE),
                                                          (currval('questions_id_seq'), 'Способность объекта изменять свою форму', FALSE),
                                                          (currval('questions_id_seq'), 'Процесс создания нового объекта', FALSE),
                                                          (currval('questions_id_seq'), 'Механизм скрытия данных объекта', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое интерфейс в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Контракт, который классы могут реализовать', TRUE),
                                                          (currval('questions_id_seq'), 'Класс, от которого могут наследовать другие классы', FALSE),
                                                          (currval('questions_id_seq'), 'Тип переменной', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который вызывается автоматически', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое абстрактный класс в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Класс, который не может быть инстанцирован и может содержать абстрактные методы', TRUE),
                                                          (currval('questions_id_seq'), 'Класс, который может быть инстанцирован', FALSE),
                                                          (currval('questions_id_seq'), 'Класс, который не может наследовать другие классы', FALSE),
                                                          (currval('questions_id_seq'), 'Класс, который может содержать только абстрактные методы', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое конструкция try-catch в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Механизм обработки исключений', TRUE),
                                                          (currval('questions_id_seq'), 'Механизм создания объектов', FALSE),
                                                          (currval('questions_id_seq'), 'Механизм наследования классов', FALSE),
                                                          (currval('questions_id_seq'), 'Механизм создания методов', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Как объявить переменную в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'type variableName;', TRUE),
                                                          (currval('questions_id_seq'), 'variableName type;', FALSE),
                                                          (currval('questions_id_seq'), 'variableName = type;', FALSE),
                                                          (currval('questions_id_seq'), 'type = variableName;', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое конструктор в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Метод, который вызывается при создании объекта', TRUE),
                                                          (currval('questions_id_seq'), 'Метод, который вызывается при уничтожении объекта', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который вызывается при изменении объекта', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который вызывается при компиляции объекта', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое static метод в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Метод, который принадлежит классу, а не объекту', TRUE),
                                                          (currval('questions_id_seq'), 'Метод, который принадлежит объекту, а не классу', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который не может быть вызван', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который вызывается при создании объекта', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое final переменная в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Переменная, значение которой не может быть изменено после присвоения', TRUE),
                                                          (currval('questions_id_seq'), 'Переменная, которая может быть изменена после присвоения', FALSE),
                                                          (currval('questions_id_seq'), 'Переменная, которая не может быть использована', FALSE),
                                                          (currval('questions_id_seq'), 'Переменная, которая вызывается при создании объекта', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое package в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Контейнер, который группирует связанные классы', TRUE),
                                                          (currval('questions_id_seq'), 'Функция, которая выполняет определенные действия', FALSE),
                                                          (currval('questions_id_seq'), 'Тип переменной', FALSE),
                                                          (currval('questions_id_seq'), 'Объект, который используется для хранения данных', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое this в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Ссылка на текущий объект', TRUE),
                                                          (currval('questions_id_seq'), 'Ссылка на следующий объект', FALSE),
                                                          (currval('questions_id_seq'), 'Ссылка на предыдущий объект', FALSE),
                                                          (currval('questions_id_seq'), 'Ссылка на класс', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое super в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Ссылка на родительский класс', TRUE),
                                                          (currval('questions_id_seq'), 'Ссылка на текущий класс', FALSE),
                                                          (currval('questions_id_seq'), 'Ссылка на интерфейс', FALSE),
                                                          (currval('questions_id_seq'), 'Ссылка на метод', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое synchronized метод в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Метод, который может быть выполнен только одним потоком одновременно', TRUE),
                                                          (currval('questions_id_seq'), 'Метод, который может быть выполнен несколькими потоками одновременно', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который не может быть выполнен', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который вызывается при создании объекта', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое многопоточность в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Способность программы выполнять несколько потоков одновременно', TRUE),
                                                          (currval('questions_id_seq'), 'Способность программы выполнять один поток', FALSE),
                                                          (currval('questions_id_seq'), 'Способность программы выполнять несколько задач', FALSE),
                                                          (currval('questions_id_seq'), 'Способность программы выполнять одну задачу', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое ArrayList в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Динамический массив, который может изменять свой размер', TRUE),
                                                          (currval('questions_id_seq'), 'Статический массив, который не может изменять свой размер', FALSE),
                                                          (currval('questions_id_seq'), 'Объект, который хранит пары ключ-значение', FALSE),
                                                          (currval('questions_id_seq'), 'Объект, который хранит уникальные значения', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое HashMap в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Объект, который хранит пары ключ-значение', TRUE),
                                                          (currval('questions_id_seq'), 'Объект, который хранит уникальные значения', FALSE),
                                                          (currval('questions_id_seq'), 'Динамический массив, который может изменять свой размер', FALSE),
                                                          (currval('questions_id_seq'), 'Статический массив, который не может изменять свой размер', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое final класс в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Класс, который не может быть унаследован', TRUE),
                                                          (currval('questions_id_seq'), 'Класс, который может быть унаследован', FALSE),
                                                          (currval('questions_id_seq'), 'Класс, который не может быть инстанцирован', FALSE),
                                                          (currval('questions_id_seq'), 'Класс, который может быть инстанцирован', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое интерфейс Serializable в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Интерфейс, который позволяет объекту быть сериализованным', TRUE),
                                                          (currval('questions_id_seq'), 'Интерфейс, который не позволяет объекту быть сериализованным', FALSE),
                                                          (currval('questions_id_seq'), 'Интерфейс, который позволяет объекту быть клонированным', FALSE),
                                                          (currval('questions_id_seq'), 'Интерфейс, который не позволяет объекту быть клонированным', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое метод equals в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Метод, который сравнивает два объекта на равенство', TRUE),
                                                          (currval('questions_id_seq'), 'Метод, который сравнивает два числа на равенство', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который сравнивает два строки на равенство', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который сравнивает два объекта на неравенство', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое метод hashCode в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Метод, который возвращает хеш-код объекта', TRUE),
                                                          (currval('questions_id_seq'), 'Метод, который возвращает длину строки', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который возвращает значение переменной', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который возвращает значение массива', FALSE);

INSERT INTO questions (subject_id, title) VALUES (java_subject_id, 'Что такое метод toString в Java?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Метод, который возвращает строковое представление объекта', TRUE),
                                                          (currval('questions_id_seq'), 'Метод, который возвращает длину строки', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который возвращает значение переменной', FALSE),
                                                          (currval('questions_id_seq'), 'Метод, который возвращает значение массива', FALSE);

-- Добавьте ещё 75 вопросов и ответов по аналогии

END $$;


-- Вставка предметной области "Linux"
INSERT INTO subjects (title) VALUES ('Linux');

-- Получение id вставленной предметной области
DO $$
DECLARE
linux_subject_id INTEGER;
BEGIN
SELECT id INTO linux_subject_id FROM subjects WHERE title = 'Linux';

-- Вставка вопросов и ответов для "Linux"
INSERT INTO questions (subject_id, title) VALUES (linux_subject_id, 'Что такое ядро Linux?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Оболочка операционной системы', FALSE),
                                                          (currval('questions_id_seq'), 'Главный компонент операционной системы, управляющий аппаратным обеспечением', TRUE),
                                                          (currval('questions_id_seq'), 'Файловая система', FALSE),
                                                          (currval('questions_id_seq'), 'Пакетный менеджер', FALSE);

INSERT INTO questions (subject_id, title) VALUES (linux_subject_id, 'Какой командой можно создать новый файл в Linux?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'mkdir', FALSE),
                                                          (currval('questions_id_seq'), 'touch', TRUE),
                                                          (currval('questions_id_seq'), 'rm', FALSE),
                                                          (currval('questions_id_seq'), 'cp', FALSE);

INSERT INTO questions (subject_id, title) VALUES (linux_subject_id, 'Какой командой можно удалить файл в Linux?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'touch', FALSE),
                                                          (currval('questions_id_seq'), 'rm', TRUE),
                                                          (currval('questions_id_seq'), 'mkdir', FALSE),
                                                          (currval('questions_id_seq'), 'mv', FALSE);

INSERT INTO questions (subject_id, title) VALUES (linux_subject_id, 'Что делает команда "ls"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Удаляет файлы', FALSE),
                                                          (currval('questions_id_seq'), 'Перемещает файлы', FALSE),
                                                          (currval('questions_id_seq'), 'Копирует файлы', FALSE),
                                                          (currval('questions_id_seq'), 'Выводит список файлов и каталогов', TRUE);

INSERT INTO questions (subject_id, title) VALUES (linux_subject_id, 'Что делает команда "cd"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Создает директорию', FALSE),
                                                          (currval('questions_id_seq'), 'Удаляет директорию', FALSE),
                                                          (currval('questions_id_seq'), 'Изменяет текущую директорию', TRUE),
                                                          (currval('questions_id_seq'), 'Копирует файлы', FALSE);

INSERT INTO questions (subject_id, title) VALUES (linux_subject_id, 'Что такое "root" в Linux?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Обычный пользователь', FALSE),
                                                          (currval('questions_id_seq'), 'Файл конфигурации', FALSE),
                                                          (currval('questions_id_seq'), 'Главный администратор системы', TRUE),
                                                          (currval('questions_id_seq'), 'Каталог', FALSE);

INSERT INTO questions (subject_id, title) VALUES (linux_subject_id, 'Что делает команда "chmod"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Изменяет права доступа к файлам и каталогам', TRUE),
                                                          (currval('questions_id_seq'), 'Изменяет владельца файла', FALSE),
                                                          (currval('questions_id_seq'), 'Показывает права доступа к файлам', FALSE),
                                                          (currval('questions_id_seq'), 'Удаляет файлы', FALSE);

INSERT INTO questions (subject_id, title) VALUES (linux_subject_id, 'Что делает команда "chown"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Изменяет права доступа к файлам и каталогам', FALSE),
                                                          (currval('questions_id_seq'), 'Изменяет владельца файла', TRUE),
                                                          (currval('questions_id_seq'), 'Показывает права доступа к файлам', FALSE),
                                                          (currval('questions_id_seq'), 'Удаляет файлы', FALSE);

INSERT INTO questions (subject_id, title) VALUES (linux_subject_id, 'Что делает команда "sudo"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Позволяет пользователю выполнять команды с правами суперпользователя', TRUE),
                                                          (currval('questions_id_seq'), 'Создает нового пользователя', FALSE),
                                                          (currval('questions_id_seq'), 'Ограничивает доступ к файлам', FALSE),
                                                          (currval('questions_id_seq'), 'Изменяет права доступа к файлам', FALSE);

INSERT INTO questions (subject_id, title) VALUES (linux_subject_id, 'Что делает команда "passwd"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Изменяет пароль пользователя', TRUE),
                                                          (currval('questions_id_seq'), 'Изменяет права доступа к файлам', FALSE),
                                                          (currval('questions_id_seq'), 'Создает нового пользователя', FALSE),
                                                          (currval('questions_id_seq'), 'Удаляет пользователя', FALSE);

-- Добавьте ещё 90 вопросов и ответов по аналогии

END $$;


-- Вставка предметной области "Docker"
INSERT INTO subjects (title) VALUES ('Docker');

-- Получение id вставленной предметной области
DO $$
DECLARE
docker_subject_id INTEGER;
BEGIN
SELECT id INTO docker_subject_id FROM subjects WHERE title = 'Docker';

-- Вставка вопросов и ответов для "Docker"
INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что такое Docker?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Система управления базами данных', FALSE),
                                                          (currval('questions_id_seq'), 'Среда виртуализации', FALSE),
                                                          (currval('questions_id_seq'), 'Платформа для разработки, доставки и запуска приложений в контейнерах', TRUE),
                                                          (currval('questions_id_seq'), 'Инструмент для разработки мобильных приложений', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что такое Docker контейнер?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Полностью изолированная виртуальная машина', FALSE),
                                                          (currval('questions_id_seq'), 'Управляемый процесс, изолированный от других процессов в системе', TRUE),
                                                          (currval('questions_id_seq'), 'Образ диска виртуальной машины', FALSE),
                                                          (currval('questions_id_seq'), 'Файл конфигурации', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что такое Docker образ?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Запущенный экземпляр приложения', FALSE),
                                                          (currval('questions_id_seq'), 'Шаблон, из которого создаются контейнеры', TRUE),
                                                          (currval('questions_id_seq'), 'Файл конфигурации', FALSE),
                                                          (currval('questions_id_seq'), 'Сетевой интерфейс', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что такое Dockerfile?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Файл конфигурации контейнера', FALSE),
                                                          (currval('questions_id_seq'), 'Файл с инструкциями по созданию Docker образа', TRUE),
                                                          (currval('questions_id_seq'), 'Файл журнала событий', FALSE),
                                                          (currval('questions_id_seq'), 'Файл с метаданными образа', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что делает команда "docker run"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Создает новый Docker образ', FALSE),
                                                          (currval('questions_id_seq'), 'Запускает новый контейнер на основе указанного образа', TRUE),
                                                          (currval('questions_id_seq'), 'Удаляет Docker контейнер', FALSE),
                                                          (currval('questions_id_seq'), 'Сохраняет изменения в контейнере', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что делает команда "docker build"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Создает новый контейнер', FALSE),
                                                          (currval('questions_id_seq'), 'Создает Docker образ из Dockerfile', TRUE),
                                                          (currval('questions_id_seq'), 'Запускает контейнер', FALSE),
                                                          (currval('questions_id_seq'), 'Удаляет образ', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что делает команда "docker ps"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Показывает все образы', FALSE),
                                                          (currval('questions_id_seq'), 'Показывает все запущенные контейнеры', TRUE),
                                                          (currval('questions_id_seq'), 'Показывает все Dockerfile', FALSE),
                                                          (currval('questions_id_seq'), 'Показывает все сохраненные изменения', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что делает команда "docker stop"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Останавливает запущенный контейнер', TRUE),
                                                          (currval('questions_id_seq'), 'Запускает новый контейнер', FALSE),
                                                          (currval('questions_id_seq'), 'Удаляет контейнер', FALSE),
                                                          (currval('questions_id_seq'), 'Сохраняет изменения в контейнере', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что делает команда "docker pull"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Удаляет Docker образ', FALSE),
                                                          (currval('questions_id_seq'), 'Скачивает Docker образ из репозитория', TRUE),
                                                          (currval('questions_id_seq'), 'Создает Docker образ из Dockerfile', FALSE),
                                                          (currval('questions_id_seq'), 'Запускает новый контейнер', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что такое Docker Hub?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Локальное хранилище Docker образов', FALSE),
                                                          (currval('questions_id_seq'), 'Облачный сервис для хранения и обмена Docker образами', TRUE),
                                                          (currval('questions_id_seq'), 'Инструмент для мониторинга контейнеров', FALSE),
                                                          (currval('questions_id_seq'), 'Файл конфигурации Docker', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что такое Docker Compose?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Инструмент для создания Docker образов', FALSE),
                                                          (currval('questions_id_seq'), 'Инструмент для запуска многоконтейнерных Docker приложений', TRUE),
                                                          (currval('questions_id_seq'), 'Инструмент для управления Docker сетями', FALSE),
                                                          (currval('questions_id_seq'), 'Инструмент для мониторинга контейнеров', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что делает команда "docker exec"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Запускает новый контейнер', FALSE),
                                                          (currval('questions_id_seq'), 'Выполняет команду в работающем контейнере', TRUE),
                                                          (currval('questions_id_seq'), 'Останавливает контейнер', FALSE),
                                                          (currval('questions_id_seq'), 'Удаляет контейнер', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что такое Docker volume?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Переменная окружения контейнера', FALSE),
                                                          (currval('questions_id_seq'), 'Механизм для хранения данных контейнеров вне контейнеров', TRUE),
                                                          (currval('questions_id_seq'), 'Сетевой интерфейс контейнера', FALSE),
                                                          (currval('questions_id_seq'), 'Файл конфигурации контейнера', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что делает команда "docker rm"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Удаляет Docker образ', FALSE),
                                                          (currval('questions_id_seq'), 'Удаляет остановленный контейнер', TRUE),
                                                          (currval('questions_id_seq'), 'Останавливает контейнер', FALSE),
                                                          (currval('questions_id_seq'), 'Создает Docker образ', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что такое Docker Network?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Инструмент для управления Docker образами', FALSE),
                                                          (currval('questions_id_seq'), 'Инструмент для создания виртуальных сетей для контейнеров', TRUE),
                                                          (currval('questions_id_seq'), 'Инструмент для мониторинга контейнеров', FALSE),
                                                          (currval('questions_id_seq'), 'Файл конфигурации Docker', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что делает команда "docker logs"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Удаляет логи контейнера', FALSE),
                                                          (currval('questions_id_seq'), 'Показывает логи запущенного контейнера', TRUE),
                                                          (currval('questions_id_seq'), 'Останавливает контейнер', FALSE),
                                                          (currval('questions_id_seq'), 'Создает логи контейнера', FALSE);

INSERT INTO questions (subject_id, title) VALUES (docker_subject_id, 'Что такое Docker Swarm?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Инструмент для мониторинга контейнеров', FALSE),
                                                          (currval('questions_id_seq'), 'Инструмент для оркестрации Docker контейнеров', TRUE),
                                                          (currval('questions_id_seq'), 'Инструмент для создания Docker образов', FALSE),
                                                          (currval('questions_id_seq'), 'Файл конфигурации Docker', FALSE);

-- Добавьте ещё 82 вопроса и ответов по аналогии

END $$;

-- Вставка предметной области "PostgreSQL"
INSERT INTO subjects (title) VALUES ('PostgreSQL');

-- Получение id вставленной предметной области
DO $$
DECLARE
postgresql_subject_id INTEGER;
BEGIN
SELECT id INTO postgresql_subject_id FROM subjects WHERE title = 'PostgreSQL';

-- Вставка вопросов и ответов для "PostgreSQL"
INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Что такое PostgreSQL?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Язык программирования', FALSE),
                                                          (currval('questions_id_seq'), 'Система управления базами данных (СУБД)', TRUE),
                                                          (currval('questions_id_seq'), 'Инструмент для анализа данных', FALSE),
                                                          (currval('questions_id_seq'), 'Фреймворк для веб-разработки', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Какой командой можно создать базу данных в PostgreSQL?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'CREATE DATABASE', TRUE),
                                                          (currval('questions_id_seq'), 'CREATE TABLE', FALSE),
                                                          (currval('questions_id_seq'), 'CREATE SCHEMA', FALSE),
                                                          (currval('questions_id_seq'), 'CREATE INDEX', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Какой командой можно удалить базу данных в PostgreSQL?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'DROP TABLE', FALSE),
                                                          (currval('questions_id_seq'), 'DROP DATABASE', TRUE),
                                                          (currval('questions_id_seq'), 'DROP SCHEMA', FALSE),
                                                          (currval('questions_id_seq'), 'DROP INDEX', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Что такое таблица в PostgreSQL?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Хранилище для ключ-значение пар', FALSE),
                                                          (currval('questions_id_seq'), 'Структура для хранения данных в строках и столбцах', TRUE),
                                                          (currval('questions_id_seq'), 'Набор индексов', FALSE),
                                                          (currval('questions_id_seq'), 'Файл конфигурации', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Какой командой можно создать таблицу в PostgreSQL?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'CREATE DATABASE', FALSE),
                                                          (currval('questions_id_seq'), 'CREATE TABLE', TRUE),
                                                          (currval('questions_id_seq'), 'CREATE SCHEMA', FALSE),
                                                          (currval('questions_id_seq'), 'CREATE INDEX', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Какой командой можно удалить таблицу в PostgreSQL?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'DROP DATABASE', FALSE),
                                                          (currval('questions_id_seq'), 'DROP TABLE', TRUE),
                                                          (currval('questions_id_seq'), 'DROP SCHEMA', FALSE),
                                                          (currval('questions_id_seq'), 'DROP INDEX', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Какой тип данных используется для хранения текстовых значений в PostgreSQL?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'INTEGER', FALSE),
                                                          (currval('questions_id_seq'), 'VARCHAR', TRUE),
                                                          (currval('questions_id_seq'), 'BOOLEAN', FALSE),
                                                          (currval('questions_id_seq'), 'DATE', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Какой тип данных используется для хранения целых чисел в PostgreSQL?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'INTEGER', TRUE),
                                                          (currval('questions_id_seq'), 'VARCHAR', FALSE),
                                                          (currval('questions_id_seq'), 'BOOLEAN', FALSE),
                                                          (currval('questions_id_seq'), 'DATE', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Что такое первичный ключ (primary key) в PostgreSQL?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Ключ, который ссылается на другую таблицу', FALSE),
                                                          (currval('questions_id_seq'), 'Ключ, который уникально идентифицирует строку в таблице', TRUE),
                                                          (currval('questions_id_seq'), 'Ключ, который может быть пустым', FALSE),
                                                          (currval('questions_id_seq'), 'Ключ, который является внешним ключом', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Что такое внешний ключ (foreign key) в PostgreSQL?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Ключ, который уникально идентифицирует строку в таблице', FALSE),
                                                          (currval('questions_id_seq'), 'Ключ, который ссылается на другую таблицу', TRUE),
                                                          (currval('questions_id_seq'), 'Ключ, который может быть пустым', FALSE),
                                                          (currval('questions_id_seq'), 'Ключ, который является первичным ключом', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Что делает команда "SELECT"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Удаляет данные из таблицы', FALSE),
                                                          (currval('questions_id_seq'), 'Обновляет данные в таблице', FALSE),
                                                          (currval('questions_id_seq'), 'Вставляет данные в таблицу', FALSE),
                                                          (currval('questions_id_seq'), 'Выбирает данные из таблицы', TRUE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Что делает команда "INSERT"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Выбирает данные из таблицы', FALSE),
                                                          (currval('questions_id_seq'), 'Вставляет данные в таблицу', TRUE),
                                                          (currval('questions_id_seq'), 'Обновляет данные в таблице', FALSE),
                                                          (currval('questions_id_seq'), 'Удаляет данные из таблицы', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Что делает команда "UPDATE"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Выбирает данные из таблицы', FALSE),
                                                          (currval('questions_id_seq'), 'Вставляет данные в таблицу', FALSE),
                                                          (currval('questions_id_seq'), 'Обновляет данные в таблице', TRUE),
                                                          (currval('questions_id_seq'), 'Удаляет данные из таблицы', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Что делает команда "DELETE"?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'Выбирает данные из таблицы', FALSE),
                                                          (currval('questions_id_seq'), 'Вставляет данные в таблицу', FALSE),
                                                          (currval('questions_id_seq'), 'Обновляет данные в таблице', FALSE),
                                                          (currval('questions_id_seq'), 'Удаляет данные из таблицы', TRUE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Какой командой можно создать индекс в PostgreSQL?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'CREATE TABLE', FALSE),
                                                          (currval('questions_id_seq'), 'CREATE DATABASE', FALSE),
                                                          (currval('questions_id_seq'), 'CREATE INDEX', TRUE),
                                                          (currval('questions_id_seq'), 'CREATE SCHEMA', FALSE);

INSERT INTO questions (subject_id, title) VALUES (postgresql_subject_id, 'Какой командой можно удалить индекс в PostgreSQL?');
INSERT INTO answers (question_id, answer, is_correct) VALUES
                                                          (currval('questions_id_seq'), 'DROP TABLE', FALSE),
                                                          (currval('questions_id_seq'), 'DROP DATABASE', FALSE),
                                                          (currval('questions_id_seq'), 'DROP INDEX', TRUE),
                                                          (currval('questions_id_seq'), 'DROP SCHEMA', FALSE);

-- Добавьте ещё 85 вопросов и ответов по аналогии

END $$;
