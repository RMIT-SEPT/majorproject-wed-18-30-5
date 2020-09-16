import React from "react";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import { render } from '@testing-library/react';
import { History } from '/.components/History';


Enzyme.configure({ adapter: new Adapter() });

describe("<History /> component rendered", () => {
    it("should render 1 <History /> component", ()=>{
        const component = shallow(<History />);
        expect(component).toHaveLength(1);
         
        });
})