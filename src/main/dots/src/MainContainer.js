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

/* The main component that holds all the other elements of the React App */

import React, {Component} from 'react';
import GridSizePicker from './GridSizePicker'
import Grid from './Grid'
import EdgeList from './EdgeList';

class MainContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      size: 3,
      edges: ""
    }
  }

  getSizeValue = (event) => {
    let value = event.target.value;

      this.setState((previousState, previousProps) => {
        return {
          size: value
        }
      });

  };

  getEdges = (event) => {
    let value = event.target.value;
    this.setState((previousState, previousProps) => {
      return {
        edges: value
      }
    });
  };

  render() {
    let gridSize = 400;
    return (
      <div>
        <GridSizePicker value={this.state.size} onChange={this.getSizeValue} />
        <Grid size={this.state.size} edges={this.state.edges} width={gridSize} height={gridSize} />
        <EdgeList value={this.state.edges} onChange={this.getEdges} rows={this.state.edges
            .split("\n").length} />
      </div>
    );
  }
}

export default MainContainer;
