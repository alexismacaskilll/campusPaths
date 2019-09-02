/*
 * Copyright ©2019 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

/* A simple grid with a variable size */
/* Most of the assignment involves changes to this class */

import React, {Component} from 'react';
import Button from './Button'

class Grid extends Component {
  constructor(props) {
    super(props);
    this.canvasReference = React.createRef();
  }

  componentDidMount() {
    this.redraw();
  }

  componentDidUpdate() {

    this.redraw();
  }

  redraw = () => {
    let ctx = this.canvasReference.current.getContext('2d');
    ctx.clearRect(0, 0, this.props.width, this.props.height);
    var background = new Image();

    background.onload = () => {
      ctx.drawImage(background,0,0);
      let coordinates = this.getCoordinates();
      coordinates.forEach(coordinate => {
        this.drawCircle(ctx, coordinate);
      });
    };
    background.src = "./image.jpg"


  };


  getCoordinates() {
    let value = this.props.size;
    let array = [];
    let k = 0;
    if (isNaN(value) || value < 0 || value > 200) {
      return [];
    } else {
      //let k = 0;
      for (let i = 0; i < value; i++) {
        for (let j = 0; j < value; j++) {
          array[k++] = [this.scalePoint(i), this.scalePoint(j)]
        }
      }
    }
    return array
  }

  //function to scale the points up to size of canvas
  scalePoint = (p) => {
      let columnSize = Math.round(400/this.props.size);
      let point = Math.round(columnSize / 2);
      return point + p * columnSize
  };

  //function to display error if input size is not valid.
  displaySize = () => {
    let value = this.props.size;
    if (value < 0 || value > 200) {
      return "Invalid Input. Grid size must be 0-200.";
    }
    else{
      return value;
    }
  };

  //function to check if input color in edge is valid color
  isColor = (inputColor) => {
      let c = new Option().style;
      c.color = inputColor;
      return c.color === inputColor;
  };



  drawEdges = () => {
    let value = this.props.edges;
      let ctx = this.canvasReference.current.getContext('2d');
      let allEdges = value.trim().split("\n");
      for (let i = 0; i < allEdges.length; i++) {
        let edgeComponents = allEdges[i].split(" ");
          if ((edgeComponents.length !== 3) || (!this.isColor(edgeComponents[2]))) {
            alert("Incorrect Edge List format. Format must be: x1,y1 x2,y2 COLOR.")
          } else {
          let points = this.parseCoordinates(edgeComponents[0].split(",")
              .concat(edgeComponents[1].split(",")));
          let color = edgeComponents[2];
          if (points.length === 4) {
            ctx.beginPath();

            ctx.moveTo(this.scalePoint(points[0]), this.scalePoint(points[1]));
            ctx.lineTo(this.scalePoint(points[2]),this.scalePoint(points[3]));
            ctx.strokeStyle = color;
            ctx.stroke()
          }
        }
      }
  };


  parseCoordinates(points) {
    let array = [];
    for (let i = 0; i < points.length; i++) {
      if ((points.length !== 4) || isNaN(parseInt(points[i])) || points[i] < 0 ||
          points[i] > (this.props.size - 1) ) {
        alert("Some edges have incorrect Edge List format. To be drawn, format " +
            "must be: x1,y1 x2,y2 COLOR.");
        return array;
      } else {
          array = array.concat(parseInt(points[i]))
      }

    }
    return array
  }

  drawCircle = (ctx, coordinate) => {
    ctx.beginPath();
    ctx.arc(coordinate[0], coordinate[1], 20 / this.props.size, 0, 2 * Math.PI);
    ctx.fill();
  };


  render() {
    return (
      <div id="canvas-div">
        <canvas ref={this.canvasReference} width={this.props.width} height={this.props.height}/>
        <div className="center-text">Current Grid Size: {this.displaySize()}</div>
        <Button color="primary" onClick={this.drawEdges} value="Draw" />
        <Button color="secondary" onClick={this.redraw} value="Clear" />
      </div>
    );
  }
}

export default Grid;
