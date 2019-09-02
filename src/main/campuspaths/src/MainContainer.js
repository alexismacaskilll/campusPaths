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
import Map from './Map';
import * as fetch from "node-fetch";
import SelectButtons from './SelectButtons';
import {Button} from "@material-ui/core";


class MainContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {

            requestResult: "NO REQUEST RESULT",
            selection1: null,
            selection2: null,
            buildings: null,
            pathJson: null

        }
    }



    componentDidMount() {
        this.makeRequest();
    }



    makeRequest = () => {
        // This does the exact same thing as makeRequestLong(), in the exact same way.
        // It's just written using a much shorter syntax with less unnecessary variables.
        // The following is basically exactly the structure of what you're going to what to
        // use in HW9 to make a request: you can model your code off of this.
        // Make sure you understand how it works, so you can modify it to do what you want!

        fetch("http://localhost:4567/getBuildings")
            .then((res) => {
                    if (res.status !== 200) {
                        throw Error("The server could not process the request.");
                    }
                    return res.json();  // Hint: res.json() might be useful here. (Read the docs for it!)
                }
            ).then(function(json) {



                return JSON.stringify(json);
            }

        ).then((resText) => {
                // resText is the value from inside of res.text(), just like before.
                //console.log(JSON.parse(resText));
                this.setState({
                    requestResult: resText,
                    buildings: JSON.parse(resText)
                });
            }
        ).catch((error) => {
            // You can also call .catch() on the return value of the .then(), instead of giving
            // a second function as a parameter. It means the same thing as above, but it's a little
            // easier to read/understand what's going on.
            alert(error);
        });
    };

    findPath = () => {
        // This does the exact same thing as makeRequestLong(), in the exact same way.
        // It's just written using a much shorter syntax with less unnecessary variables.
        // The following is basically exactly the structure of what you're going to what to
        // use in HW9 to make a request: you can model your code off of this.
        // Make sure you understand how it works, so you can modify it to do what you want!
        if (this.state.selection1 != null && this.state.selection2 != null  ) {
            var url = "http://localhost:4567/getPaths?start=" + this.state.selection1 + "&dest=" + this.state.selection2;

            fetch(url)
                .then((res) => {
                        if (res.status !== 200) {
                            throw Error("The server could not process the request.");
                        }
                        return res.json();  // Hint: res.json() might be useful here. (Read the docs for it!)
                    }
                ).then(function (json) {

                    return JSON.stringify(json);

                }
            ).then((resText) => {
                    this.setState({

                        pathJson: JSON.parse(resText)
                    });
                }
            ).catch((error) => {
                // You can also call .catch() on the return value of the .then(), instead of giving
                // a second function as a parameter. It means the same thing as above, but it's a little
                // easier to read/understand what's going on.
                alert(error);
            });
        }
    };


    clearSelections() {
        this.setState({

            selection1: null,
            selection2: null,
            pathJson: null
        });
    }


    handleSelection1(event) {
        this.setState({
            selection1: event.target.value
        });
    }

    handleSelection2(event) {
        this.setState({
            selection2: event.target.value
        });
    }

    render() {


        return (
            <div>


                <Map start={this.state.selection1} dest={this.state.selection2} path={this.state.pathJson} />
                <p>
                    UW Campus Pathfinder
                </p>

                <SelectButtons value={this.state.selection1} select = {"Start Building"} buildings = {this.state.buildings} onChange={(event) => {this.handleSelection1(event)}} />
                <SelectButtons value={this.state.selection2} select = {"End Building"} buildings = {this.state.buildings} onChange={(event) => {this.handleSelection2(event)}} />
                <Button variant="contained" onClick={this.findPath}>Find Path</Button>
                <Button variant="contained" onClick={this.clearSelections.bind(this)}>Clear Path</Button>


            </div>
        );
    }
}

export default MainContainer;
