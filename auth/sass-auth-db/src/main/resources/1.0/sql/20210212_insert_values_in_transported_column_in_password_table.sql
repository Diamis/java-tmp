UPDATE password p 
SET transported = false
WHERE (user_info_id = created_by AND user_info_id NOT IN (SELECT p.user_info_id FROM password p INNER JOIN user_sso_info usi ON p.user_info_id = usi.user_info_id))
    OR user_info_id IN (SELECT user_info_id FROM password GROUP BY user_info_id HAVING count(*) > 1);