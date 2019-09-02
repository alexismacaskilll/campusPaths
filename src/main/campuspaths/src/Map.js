import React, {Component} from 'react';
import "./Map.css";

class Map extends Component {

  // NOTE:
  // This component is a suggestion for you to use, if you would like to.
  // It has some skeleton code that helps set up some of the more difficult parts
  // of getting <canvas> elements to display nicely.
  //
  // If you don't want to use this component, you're free to delete it.

  constructor(props) {
    super(props);
    this.canvasReference = React.createRef();
    this.backgroundImage = new Image();
    this.backgroundImage.src = "./campus_map.jpg";
    this.backgroundImage.onload = () => {
      this.drawBackgroundImage()
    };


  }

  componentDidMount() {
    this.drawBackgroundImage();
  }

  componentDidUpdate() {
    this.drawBackgroundImage();

  }

  drawBackgroundImage() {
    let canvas = this.canvasReference.current;
    let ctx = canvas.getContext("2d");
    if (this.backgroundImage.complete) { // This means the image has been loaded.
      canvas.width = this.backgroundImage.width;
      canvas.height = this.backgroundImage.height;
      ctx.drawImage(this.backgroundImage, 0, 0);


      let segments = this.getEdges();
      segments.forEach(segment => {

        this.drawEdge(ctx, segment);

      });

      let coordinates = this.getCoordinates();
      coordinates.forEach(coordinate => {
        this.drawCircle(ctx, coordinate);
      });
      if (coordinates.length > 0) {

      this.drawStart(ctx, coordinates[0]);
      this.drawEnd(ctx, coordinates[coordinates.length - 1])
    }


    }
  }



  getCoordinates() {

    let jsonPath = this.props.path; //cost start path[]
    if (jsonPath === null){
      return [];
    }
    else {
      let pathArray = jsonPath.path;   //path array
      let array = [];
      let k = 0;

      let last = -1;
      for (var i = 0; i < pathArray.length; i++) {

        let singleSegment = pathArray[i];  //single start, end, cost
        array[k++] = [parseInt(singleSegment.start.x), parseInt(singleSegment.start.y)];
        last = i;
      }
      if(pathArray.length > 0) {

        let singleSegment = pathArray[pathArray.length - 1];
        array[k] =  [parseInt(singleSegment.end.x), parseInt(singleSegment.end.y)];
      }


      return array
    }


  }


  getEdges = () => {

    let jsonPath = this.props.path; //cost start path[]
    if (jsonPath === null){
      return [];
    }
    else {
      let pathArray = jsonPath.path;   //path array
      let array = [];
      let k = 0;
      for (var i in pathArray) {

        let singleSegment = pathArray[i];  //single start, end, cost
        array[k++] = [parseInt(singleSegment.start.x), parseInt(singleSegment.start.y), parseInt(singleSegment.end.x), parseInt(singleSegment.end.y)]

      }

      return array
    }

  };


  drawCircle = (ctx, coordinate) => {

    ctx.beginPath();
    ctx.arc(coordinate[0], coordinate[1], 10, 0, 2 * Math.PI);
    ctx.fillStyle = "orange";
    ctx.fill();
  };

  drawStart = (ctx, coordinate) => {

    ctx.beginPath();
    ctx.arc(coordinate[0], coordinate[1], 18, 0, 2 * Math.PI);
    ctx.fillStyle = "green";
    ctx.fill();
  };

  drawEnd = (ctx, coordinate) => {

    ctx.beginPath();
    ctx.arc(coordinate[0], coordinate[1], 18, 0, 2 * Math.PI);
    ctx.fillStyle = "red";
    ctx.fill();
  };


  drawEdge = (ctx, segment) => {
    ctx.beginPath();
    ctx.strokeStyle = "blue";
    ctx.lineWidth = 5;
    ctx.moveTo(segment[0],segment[1]);
    ctx.lineTo(segment[2], segment[3]);

    ctx.stroke()
  };


  render() {
    // that's set up to center the canvas for you. See Map.css for more details.
    // Make sure you set up the React references for the canvas correctly so you
    // can get the canvas object and call methods on it.
    return (

      <div className="canvasHolder">

        <canvas ref={this.canvasReference} width={this.props.width} height={this.props.width}/>


      </div>
    )
  }
}

export default Map;