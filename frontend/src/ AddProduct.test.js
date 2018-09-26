import React from 'react';
import AddProduct from './components/AddProduct';
import Enzyme, { shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';

Enzyme.configure({ adapter: new Adapter() });
describe('<AddProduct />', () => {
    it('renders five <TextInput /> components', () => {
        const wrapper = shallow( < AddProduct / > );
        expect(wrapper.find('TextField')).toHaveLength(4);
    });
});