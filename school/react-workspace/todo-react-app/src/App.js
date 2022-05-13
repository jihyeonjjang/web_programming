import React from "react";
import "./App.css";
import Todo from "./Todo";
import { Paper, List, Container } from "@material-ui/core";
import AddTodo from "./AddTodo.js";
import { call } from "./service/ApiService";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      items: [],
    };
  }

  componentDidMount() {
    call("/todo", "GET", null).then((response) => {
      this.setState({ items: response.data });
    });
  }

  add = (item) => {
    /*
    const thisItems = this.state.items;
    item.id = "ID-" + thisItems.length;
    item.done = false;
    thisItems.push(item);
    this.setState({ itmes: thisItems });
    console.log("items: ", this.state.items);
    */
    call("/todo", "POST", item).then((response) => {
      this.setState({ items: response.data });
    });
  };

  delete = (item) => {
    /*
    const thisItems = this.state.items;
    console.log("Before delete items", this.state.items);

    const newItems = thisItems.filter((e) => e.id !== item.id);

    this.setState({ items: newItems }, () => {
      console.log("After delete items", this.state.items);
    });
    */
    call("/todo", "DELETE", item).then((response) => {
      this.setState({ items: response.data });
    });
  };

  update = (item) => {
    call("/todo", "PUT", item).then((response) => {
      this.setState({ items: response.data });
    });
  };

  render() {
    // <Todo> 컴포넌트 배열
    var todoItems = this.state.items.length > 0 && (
      <Paper>
        <List>
          {this.state.items.map((item, idx) => (
            <Todo
              item={item}
              key={item.id}
              delete={this.delete}
              update={this.update}
            />
          ))}
        </List>
      </Paper>
    );

    return (
      <div className="App">
        <Container maxWidth="md">
          <AddTodo add={this.add} />
          <div className="TodoList">{todoItems}</div>;
        </Container>
      </div>
    );
  }
}

export default App;
