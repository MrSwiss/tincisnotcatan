var ROAD_WIDTH_SCALE = 0.05;
var ROAD_LENGTH_SCALE = 1.0;

function Road(start1, start2, start3, end1, end2, end3) {
	this.start = findCenter(start1, start2, start3);
	this.end = findCenter(end1, end2, end3);
	this.id = ("road-x-" + this.start.x + "y-" + this.start.y + "z-" + this.start.z
				+ "-to-x-" + this.end.x + "y-" + this.end.y + "z-" + this.end.z).replace(/[.]/g, "_");
	this.containsRoad = false;
	this.player = null;
	
	$("#board-viewport").append("<div class='road' id='" + this.id + "'></div>");
}

Road.prototype.draw = function(transX, transY, scale) {
	if (this.containsRoad) {
		var cartesianStart = hexToCartesian(this.start);
		var cartesianEnd = hexToCartesian(this.end);
		var x = transX + cartesianStart.x * scale + Math.sqrt(3) * scale / 4;
		var y = transY + cartesianStart.y * scale + scale / 4;
		
		var cartesianStart = hexToCartesian(this.start);
		var cartesianEnd = hexToCartesian(this.end);
		
		var deltaX = cartesianEnd.x - cartesianStart.x;
		var deltaY = cartesianEnd.y - cartesianStart.y;
		
		var angle = Math.atan(deltaY / deltaX);
		if (deltaX < 0) {
			angle = angle + Math.PI;
		}
		
		var length = scale / Math.sqrt(3) * ROAD_LENGTH_SCALE;
		var height = scale * ROAD_WIDTH_SCALE;

		var diag = Math.sqrt(Math.pow(length, 2) + Math.pow(height, 2)) / 2;
		var diagAngle = Math.atan(height / length);
		
		x = x + 0.04 * scale;
//		x = x + (scale / Math.sqrt(3)) - (length / 2);
		
		if (angle == 0 || angle == Math.PI || angle == -Math.PI) {
//			x = x - ((scale / Math.sqrt(3)) - (length / 2));
			console.log(angle);
		} else if (angle == Math.PI / 3) {
			console.log(angle);
		} else if (angle == -Math.PI / 3) {
			console.log(angle);
		}
		console.log(angle - Math.PI / 3);

		// Account for rotation
//		x = x - (diag * Math.cos(diagAngle) - diag * Math.cos(diagAngle + angle));
//		y = y - (diag * Math.sin(diagAngle) - diag * Math.sin(diagAngle + angle));
		
		// Center between hexagons
		// if (angle !== 0 && angle !== Math.PI && angle !== -Math.PI) {
		// 	x = x + (height / (2 * Math.sin(angle)));
		// }

		// Account for rotation
		// x = x - (length / 2) * (1 - Math.cos(-Math.atan(height / length) + angle));
		// y = y + (length / 2) * Math.sin(-Math.atan(height / length) + angle);
		
		// Center between hexagons
		// x = x + (height * Math.sqrt(3) / 2);
		
		var element = $("#" + this.id);
		
		element.css("transform", "translate(" + x + "px, " + y + "px) "
				+ "rotate(" + angle + "rad)");
		element.css("width", length);
		element.css("height", height);
		element.css("background-color", this.player.color);
	}
}

Road.prototype.addRoad = function(player) {
	this.containsRoad = true;
	this.player = player;
}
