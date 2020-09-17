import React from 'react';
import { render } from '@testing-library/react';
import App from './App';
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import Booking from './components/Booking';
import Login from './components/Login';
import Signup from './components/Signup';
import Home from './components/Home';

Enzyme.configure({ adapter: new Adapter() });

describe('Booking', () => {
 const onChange = jest.fn();
 let wrapper;
 beforeEach(() =>{
 wrapper = shallow(<Booking onChange={onChange} />)
 })
 it('renders', () =>{
 expect(wrapper).not.toBeNull();
 })
});

describe('Login', () => {
  
  let wrapper;
  beforeEach(() =>{
  wrapper = shallow(<Login />)
  })
  it('renders', () =>{
  expect(wrapper).not.toBeNull();
  })
 });

describe('Home', () => {
  
  let wrapper;
  beforeEach(() =>{
  wrapper = shallow(<Home />)
  })
  it('renders', () =>{
  expect(wrapper).not.toBeNull();
  })
 });

 describe('Signup', () => {
  
  let wrapper;
  beforeEach(() =>{
  wrapper = shallow(<Signup />)
  })
  it('renders', () =>{
  expect(wrapper).not.toBeNull();
  })
 });