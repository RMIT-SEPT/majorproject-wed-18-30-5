import React from 'react';
import Login from './Login';
import { shallow, mount } from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter() });

describe('Test case for testing login',() =>{
    let wrapper;
    test('username check',()=>
    {
    wrapper = shallow(<Login/>);
    wrapper.find('input[id="email"]')
    wrapper.simulate('change', {target: {name: 'username', value: 'test@test.com'}});
    expect(wrapper.find('input[id="email"]')).toEqual('test@test.com');
    })
    it('password check',()=>{
    wrapper = shallow(<Login/>);
    wrapper.find('input[type="password"]');
    wrapper.simulate('change', {target: {name: 'password', value: 'testpassword123'}});
    expect(wrapper.find('password')).toEqual('testpassword123');
    })
    // it('login check with right data',()=>{
    // wrapper = shallow(<Login/>);
    // wrapper.find('input[type="text"]');
    // wrapper.simulate('change', {target: {name: 'username', value: 'krishankantsinghal'}});
    // wrapper.find('input[type="password"]');
    // wrapper.simulate('change', {target: {name: 'password', value: 'krishankant123'}});
    // wrapper.find('button');
    // wrapper.simulate('click');
    // expect(wrapper.find('isLogined')).toBe(true);
    // })
    // it('login check with wrong data',()=>{
    // wrapper = shallow(<Login/>);
    // wrapper.find('input[type="text"]');
    // wrapper.simulate('change', {target: {name: 'username', value: 'krishankantsinghal'}});
    // wrapper.find('input[type="password"]');
    // wrapper.simulate('change', {target: {name: 'password', value: 'krishankant1234'}});
    // wrapper.find('button');
    // wrapper.simulate('click');
    // expect(wrapper.find('isLogined')).toBe(false);
    // })
    })