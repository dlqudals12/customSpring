import "./App.css";
import axios from "axios";

function App() {
  const onClick = () => {
    axios.get("/maps/request?name=front").then((res) => {
      console.log(res);
    });
  };

  return (
    <>
      <div>
        <button onClick={onClick}>클릭</button>
      </div>
    </>
  );
}

export default App;
