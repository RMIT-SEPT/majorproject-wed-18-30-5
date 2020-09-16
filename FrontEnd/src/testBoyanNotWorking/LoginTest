import React from "react";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import { render } from '@testing-library/react';
import { Login } from '/.components/Login';


Enzyme.configure({ adapter: new Adapter() });

describe("<Login /> component rendered", () => {
    it("should render 1 <Login /> component", ()=>{
        const component = shallow(<Login />);
        expect(component).toHaveLength(1);
         
        });  
})