import './App.css';
import axios from 'axios';
import { Container, Text, Button } from 'typetailui';

function App() {
  const onRegister = async () => {
    try {
      const data = {
        email: "thaovu@miu.edu",
        password: "123"
      }
      const endpoint = "http://localhost:6001/api/users/register";
      const response = await axios.post(endpoint, data);
      console.log(response.data);
    } catch (error) {
      
    }
  }
  const onLogin = async () => {
    try {
      const data = {
        email: "thaovu@miu.edu",
        password: "123"
      }
      const endpoint = "http://localhost:6001/api/users/login";
      const response = await axios.post(endpoint, data);
      console.log(response.data);
    } catch (error) {
      
    }
  }
  const onHello = async () => {
    try {
      const token = "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRoYW92dUBtaXUuZWR1Iiwicm9sZSI6ImRldiIsImlhdCI6MTcxMTU1NTg5MCwiZXhwIjoxNzExNTkxODkwfQ.HagmGRUbpStkXhvjvgMgqPhO94p9uqFCtpEzlT7P4X0";
      const endpoint = "http://localhost:6001/api/users/hello";
      const response = await axios.get(endpoint, {
        headers:{
          "Authorization": `Bearer ${token}`
        }
      });
      console.log(response.data);
    } catch (error) {
      
    }
  }
  return (
    <Container className='flex flex-col justify-center items-center space-y-2'>
      <Text>Welcome to Sping-Boot-React-MongoDB Demo</Text>
      <Button title="Register" onClick={onRegister}></Button>
      <Button title="Login" onClick={onLogin}></Button>
      <Button title="Hello" onClick={onHello}></Button>
    </Container>
  );
}

export default App;
