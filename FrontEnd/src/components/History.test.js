import React from 'react';
import History from './History';
import { shallow } from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter() });

describe('History', () => {
    it('renders', () => {
      const component = shallow(<History />);
      expect(component).toBeTruthy();
    })
})