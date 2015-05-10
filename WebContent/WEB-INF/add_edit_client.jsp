<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
	<title>
		<c:set var="isEdit" value="false" /> <c:choose>
			<c:when test="${not empty client}">
				<c:set var="isEdit" value="true" />
				${client.getName()}
			</c:when>
			<c:otherwise>
				Add new
			</c:otherwise>
		</c:choose>
	</title>

	<link rel="stylesheet" href="./view/css/general.css" media="screen" type="text/css" />
	<script type="text/javascript" src="./view/scripts/js/lib/jquery-2.1.1.min.js"></script>
</head>

<body>
	<c:import url="./include/top.jsp"></c:import>

	<div class="wrapper">
		<c:if test="${not empty param.error}">
			<div class="error">Something went wrong. Please try again
				later.</div>
		</c:if>

		<c:if test="${not empty param.success}">
			<div class="success">You successfully saved your changes.</div>
		</c:if>
		
		<c:choose>
			<c:when test="${isEdit}">
				<form action="./AddEdit?clientId=${client.getId()}"  method="POST" enctype="multipart/form-data">
				<input type="hidden" name="requestType" value="edit" />
			</c:when>
			<c:otherwise>
				<form action="./AddEdit?add=new" method="POST"  enctype="multipart/form-data">
				<input type="hidden" name="requestType" value="add" />
			</c:otherwise>
		</c:choose>

		<table id="clients">
			<caption>
				<c:choose>
					<c:when test="${isEdit}">
							Edit ${client.getName()}
						</c:when>
					<c:otherwise>
							Add new client
						</c:otherwise>
				</c:choose>
			</caption>
			<thead>
				<tr>
					<th>Company Name</th>
					<th>Office Location</th>
					<th>Additional Notes</th>
					<th>Date of Contract <br /> /dd.mm.yyyy/
					</th>
					<th>Logo</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input class="name" class="expanded-textbox" type="text" name="name"
						placeholder="name" value="${client.getName()}" maxlength="50" /></td>
					<td><input class="expanded-textbox" type="text"
						name="location" placeholder="location"
						value="${client.getLocation()}" maxlength="50" /></td>
					<td><textarea class="notes-area" name="notes"
							placeholder="notes" maxlength="100000">${client.getNotes()}</textarea></td>
					<td><input class="date" type="text" name="contractDate"
						placeholder="contract date" value="${client.getContractDate()}" /></td>
					<td>
					<c:if test="${not empty client.getLogoFileName()}" >
						<img src="Image?name=${client.getLogoFileName()}" alt="${client.getLogoFileName()}" /> <br />
					</c:if>
					<input type="file" name="logoFile"></input></td>
				</tr>
			</tbody>
		</table>
		<br />
		<input type="submit" value="Save" />
		<input type="button" onclick="history.go(-1);" value="Cancel" />
		</form>
	</div>
	<script src="./view/scripts/js/validate-date-input.js"></script>
	<script src="./view/scripts/js/validate-name-input.js"></script>
	<script>
		$('.name').focus();
	</script>
</body>
</html>