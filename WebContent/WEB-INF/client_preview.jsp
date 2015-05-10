<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Company preview - ${client.getName()}</title>
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
		
		<table id="clients">
			<caption>Company preview - ${client.getName()}</caption>
			<thead>
				<tr>
					<th>Company Name</th>
					<th>Office Location</th>
					<th>Additional Notes</th>
					<th>Date of Contract</th>
					<th>Logo</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
					<c:choose>
						<c:when test="${client.getName() != null}">
							${client.getName()}
						</c:when>
						<c:otherwise>
							Some error occurred. Please try again later.
						</c:otherwise>
					</c:choose></td>
					<td>
					<c:choose>
						<c:when test="${client.getLocation() != null}">
							${client.getLocation()}
						</c:when>
						<c:otherwise>
							No location.
						</c:otherwise>
					</c:choose></td>
					<td>
					<c:choose>
						<c:when test="${not empty client.getNotes()}">
							${client.getNotes()}
						</c:when>
						<c:otherwise>
							No notes.
						</c:otherwise>
					</c:choose></td>
					<td>
					<c:choose>
						<c:when test="${client.getContractDate() != null}">
							${client.getContractDate()}
						</c:when>
						<c:otherwise>
							No contract date.
						</c:otherwise>
					</c:choose></td>
					<td>
					<c:choose>
						<c:when test="${client.getLogoFileName() != null}">
							<img src="Image?name=${client.getLogoFileName()}" alt="${client.getLogoFileName()}" />
						</c:when>
						<c:otherwise>
							No logo.
						</c:otherwise>
					</c:choose></td>
					<td><a class="edit" data-id="${client.getId()}">Edit</a></td>
					<td><a class="delete" data-id="${client.getId()}">Delete</a></td>
				</tr>
			</tbody>
		</table>
	</div>

	<script src="./view/scripts/js/database-operations-requester.js"></script>
</body>
</html>