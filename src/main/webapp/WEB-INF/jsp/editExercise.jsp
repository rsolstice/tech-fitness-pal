<%@ page import="com.techelevator.model.dto.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<c:url var="validationJs" value="/js/user-validation.js" />
<script src="${validationJs}"></script>

<c:set var="currentExercise" scope="session" value="${currentExercise}"/>

<c:url var="formAction" value="/users/custom-exercise/edit/${currentExercise.id}" />

<form method="POST" action="${formAction}">
  <input type="hidden" name="CSRF_TOKEN" value="${CSRF_TOKEN}"/>

  <div class="row">
    <div class="col-sm-4"></div>
    <div class="col-sm-4">

      <div class="form-group">
        <label for="exerciseName">Exercise Name: </label>
        <input type="text" id="exerciseName" value="${currentExercise.exerciseName}" name="exerciseName" placeHolder="Exercise Name" class="form-control" />
      </div>

      <div class="form-group">
        <label for="reps">Reps: </label>
        <input type="text" id="reps" value="${currentExercise.reps}" name="reps" placeHolder="Reps" class="form-control" />
      </div>

      <div class="form-group">
        <label for="sets">sets: </label>
        <input type="text" id="sets" value="${currentExercise.sets}" name="sets" placeHolder="Sets" class="form-control" />
      </div>

      <div class="form-group">
        <label for="calories">calories: </label>
        <input type="text" id="calories" value="${currentExercise.calories}" name="calories" placeHolder="Calories" class="form-control" />
      </div>

      <button type="submit" class="btn btn-primary">Update Exercise</button>

    </div>
    <div class="col-sm-4"></div>
  </div>


</form>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />