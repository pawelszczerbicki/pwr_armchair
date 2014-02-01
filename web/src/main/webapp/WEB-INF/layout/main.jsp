<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <tiles:insertAttribute name="header"/>
</head>
<body ws-url="<c:url value="/rest/message/device"/>">

<tiles:insertAttribute name="body"/>

<tiles:insertAttribute name="footer"/>

</body>
</html>



