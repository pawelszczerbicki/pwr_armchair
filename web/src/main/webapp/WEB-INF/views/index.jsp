<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Car seat control panel</title>

    <link href="css/style.css" rel="stylesheet">
    <link href="css/pathslider.css" rel="stylesheet">

</head>

<body>
<div class="canvas-container">
    <div id="image-container">
        <img src="#" style="display:none">
    </div>
    <div id="slider-top-container">
        <div id="slider-top"></div>
    </div>
    <div id="slider-header-h-container">
        <div id="slider-header-h" class="hide"></div>
    </div>
    <div id="slider-header-v-container">
        <div id="slider-header-v" class="hide"></div>
    </div>
    <div id="slider-middle-h-container">
        <div id="slider-middle-h" class="hide"></div>
    </div>
    <div id="slider-middle-v-container">
        <div id="slider-middle-v" class="hide"></div>
    </div>
    <div id="slider-bottom-h-container">
        <div id="slider-bottom-h" class="hide"></div>
    </div>
    <div id="slider-bottom-v-head-container">
        <div id="slider-bottom-v-head" class="hide"></div>
    </div>
    <div id="slider-bottom-v-tail-container">
        <div id="slider-bottom-v-tail" class="hide"></div>
    </div>
    <div id="slider-bottom-container">
        <div id="slider-bottom"></div>
    </div>
    <a id="btn-head" href="javascript:void(0)" class="btn-main">+</a>
    <a id="btn-middle" href="javascript:void(0)" class="btn-main">+</a>
    <a id="btn-bottom" href="javascript:void(0)" class="btn-main">+</a>
    <canvas id="canvas-seat-control" width="600" height="600"></canvas>
</div>

<!-- JavaScript -->
<script src="js/jquery-2.0.3.min.js"></script>
<script src="js/jquery.pathslider.min.js"></script>
<script src="js/main.js"></script>

</body>
</html>
