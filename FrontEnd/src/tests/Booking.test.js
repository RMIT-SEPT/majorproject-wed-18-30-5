import React from "react";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import { render } from '@testing-library/react';
import { Booking } from '/.components/Booking';


Enzyme.configure({ adapter: new Adapter() });

describe("<Booking /> component rendered", () => {
    it("should render 1 <Booking /> component", ()=>{
        const component = shallow(<Booking />);
        expect(component).toHaveLength(1);
         
        });
})