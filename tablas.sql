CREATE TABLE IF NOT EXISTS school (
  id SERIAL PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  city VARCHAR(100) NOT NULL,
  type VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS career (
  id SERIAL PRIMARY KEY,
  name VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS inscription (
  id SERIAL PRIMARY KEY,
  applicant_name VARCHAR(200) NOT NULL,
  applicant_cedula CHAR(10) NOT NULL,
  applicant_email VARCHAR(200) NOT NULL,
  school_id INT NOT NULL REFERENCES school(id),
  career_id INT NOT NULL REFERENCES career(id),
  amount_paid NUMERIC(10,2) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Seeds de colegios (Tabla 1)
INSERT INTO school (name, city, type) VALUES
 ('Técnico Ecuador','Cuenca','FISCAL'),
 ('Técnico Ecuador','Quito','FISCAL'),
 ('Benigno Malo','Quito','FISCAL'),
 ('Camilo Gallegos','Gualaquiza','FISCAL'),
 ('Camilo Gallegos','Quito','FISCAL'),
 ('Borja','Cuenca','PARTICULAR'),
 ('Catalinas','Cuenca','PARTICULAR'),
 ('Técnico Salesiano','Cuenca','FISCOMISIONAL')
ON CONFLICT DO NOTHING;

-- Seeds de carreras (Tabla 2)
INSERT INTO career (name) VALUES
 ('Medicina'),
 ('Ingeniería Civil'),
 ('Software'),
 ('Administración de empresas')
ON CONFLICT DO NOTHING;