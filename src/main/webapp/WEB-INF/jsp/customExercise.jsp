<%@ page import="com.techelevator.model.dto.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<c:url var="validationJs" value="/js/user-validation.js" />
<script src="${validationJs}"></script>

<c:url var="formAction" value="/users/custom-exercise" />
<c:url var="cancelAction" value="/users/exerciseForm" />

<c:set var="currentExercise" scope="session" value="${exercise}"/>

<form method="POST" action="${formAction}">
    <input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>

    <div class="row form-container">

        <div class="col-sm-4 form-column">

            <div class="form-group">
                <label for="exerciseName">Exercise Name: </label>
                <input type="text" id="exerciseName" value="${exercise.exerciseName}" name="exerciseName" placeHolder="Exercise Name" class="form-control" />
            </div>

            <div class="form-group">
                <label for="reps">Reps: </label>
                <input type="text" id="reps" value="${exercise.reps}" name="reps" placeHolder="Reps" class="form-control" />
            </div>

            <div class="form-group">
                <label for="sets">Sets: </label>
                <input type="text" id="sets" value="${exercise.sets}" name="sets" placeHolder="Sets" class="form-control" />
            </div>

            <div class="form-group">
                <label for="calories">Calories: </label>
                <input type="text" id="calories" value="${exercise.calories}" name="calories" placeHolder="Calories" class="form-control" />
            </div>


            <a href="${cancelAction}" class="btn btn-primary">Cancel</a>
            <button type="submit" class="btn btn-primary">Create Exercise</button>
        </div>
    </div>
</form>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />