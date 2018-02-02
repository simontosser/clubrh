import { BaseEntity } from './../../shared';

export class Address implements BaseEntity {
    constructor(
        public id?: number,
        public at?: string,
        public dispatch?: string,
        public address?: string,
        public additionalAddress?: string,
        public zipCode?: string,
        public city?: string,
        public companyId?: number,
    ) {
    }
}
