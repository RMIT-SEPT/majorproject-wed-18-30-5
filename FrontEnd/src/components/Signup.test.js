import React from 'react';
import Signup from './Signup';
import { shallow } from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter() });

describe('Signup', () => {
    it('renders', () => {
      const component = shallow(<Signup />);
  
      expect(component).toBeTruthy();
    })
})