-- 300-301

-- Reset the database version to correspond to the Agilefant version (e.g. undo the pre-alpha versioning)
INSERT INTO settings (`name`, `value`, `description`) VALUES ('AgilefantDatabaseVersion', '301', 'Agilefant database version') ON DUPLICATE KEY UPDATE `value`="301";

-- Removes the faulty ranks (which cause 'undefined' stories to appear in iterations) from stories in iterations:
DELETE storyrank FROM storyrank INNER JOIN stories ON stories.id = storyrank.story_id WHERE (stories.backlog_id IS NULL OR storyrank.backlog_id <> stories.backlog_id) AND storyrank.backlog_id <> stories.iteration_id;

-- Removes the faulty ranks (which cause 'undefined' stories to appear in iterations) from stories in products and/or projects
DELETE storyrank FROM storyrank INNER JOIN stories ON stories.id = storyrank.story_id WHERE (stories.iteration_id IS NULL) AND storyrank.backlog_id <> stories.backlog_id;

-- Inserts the ranks to the stories which have had their rank set for the wrong iteration, thus making them "re-appear" in their correct iterations once more
INSERT INTO storyrank (story_id,backlog_id,rank) (SELECT id,iteration_id, (SELECT IFNULL(MAX(rank)+1,0) FROM storyrank WHERE storyrank.backlog_id = iteration_id) AS rank FROM stories WHERE id NOT IN (SELECT story_id FROM storyrank INNER JOIN backlogs ON backlogs.id = storyrank.backlog_id WHERE backlogs.backlogtype = 'Iteration') AND iteration_id IS NOT NULL);