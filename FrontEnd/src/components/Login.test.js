import React from 'react';
import Login from './Login';
import { mount } from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter() });

describe('Login tests', () => {
    it('should find href link', () => {
        const component = mount(<Login />);
        expect(component.find('[href="/Signup"]').length).toEqual(5);
    })
    
    it('should input username', () => {
        const component = mount(<Login />);
        const inputname = component.find('TextField#username').hostNodes();
        inputname.instance().value = 'Jacob';
        inputname.simulate('change');
        expect(inputname.instance().value).toEqual('Jacob');
    });

});

