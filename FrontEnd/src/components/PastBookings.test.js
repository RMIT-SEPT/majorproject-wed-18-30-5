import React from 'react';
import PastBookings from './PastBookings';
import { shallow } from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter() });

describe('PastBookings', () => {
    it('renders', () => {
      const component = shallow(<PastBookings />);
  
      expect(component).toBeTruthy();
    })
})