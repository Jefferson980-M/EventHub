ALTER TABLE events ADD COLUMN end_date DATE;
UPDATE events SET end_date = start_date;
ALTER TABLE events ALTER COLUMN end_date SET NOT NULL;
