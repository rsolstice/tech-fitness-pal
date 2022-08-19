package com.techelevator.model.dao.jdbc;

import com.techelevator.model.dao.ExerciseDAO;
import com.techelevator.model.dto.Exercise;
import com.techelevator.model.dto.User;
import com.techelevator.model.dto.Workout;
import com.techelevator.services.security.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Locale;

@Component
public class JDBCExerciseDAO implements ExerciseDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCExerciseDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveExercise(String exerciseName, int calories, int reps, int sets, String category) {

        jdbcTemplate.update("INSERT INTO exercise(exercise_name, calories, reps, sets) VALUES (?, ?, ?, ?)",
                exerciseName, calories, reps, sets, category
        );
    }

    @Override
    public void createExercise(Workout workout, Exercise exercise) {
        jdbcTemplate.update(
                "INSERT INTO exercise(workout_id, exercise_name, reps, sets, calories) VALUES (?, ?, ?, ?, ?)",
                workout.getId(), exercise.getExerciseName(), exercise.getReps(), exercise.getSets(), exercise.getCalories()
        );
    }

    @Override
    public Object getExerciseByExerciseName(String workoutName, String exerciseName) {
        String sqlSearchForExercise ="SELECT e.id, e.workout_id, e.exercise_name, e.calories, e.reps, e.sets " +
                "FROM workout as w " +
                "LEFT JOIN exercise e " +
                "ON w.id = e.workout_id "+
                "WHERE UPPER(workout_name) = ? " +
                "AND exercise_name = ?";

        SqlRowSet exercise = jdbcTemplate.queryForRowSet(sqlSearchForExercise, workoutName, exerciseName);
        Exercise thisExercise = null;
        if(exercise.next()) {
            thisExercise = new Exercise();
            thisExercise.setId(exercise.getInt("id"));
            thisExercise.setExerciseName(exercise.getString("exercise_name"));
            thisExercise.setReps(exercise.getInt("reps"));
            thisExercise.setSets(exercise.getInt("sets"));
            thisExercise.setCalories(exercise.getInt("calories"));
        }

        return thisExercise;
    }

    @Override
    public void updateExercise(Workout workout, Exercise exercise) {
        jdbcTemplate.update("UPDATE exercise " +
                        "SET exercise_name = ?, " +
                        "reps = ?, " +
                        "sets = ? " +
//                        "total_calories = ? " +
                        "FROM workout as w " +
                        "LEFT JOIN exercise e " +
                        "ON w.id = e.workout_id "+
                        "WHERE workout_name = ? " +
                        "AND exercise_name = ?",
                exercise.getExerciseName(), exercise.getReps(),
                exercise.getSets(), workout.getWorkoutName(), exercise.getExerciseName());
    }

    @Override
    public void deleteExercise(Workout workout, Exercise exercise) {

        jdbcTemplate.update("DELETE FROM exercise " +
                        "WHERE exercise_name = ? ",
                        exercise.getExerciseName());
    }

}
