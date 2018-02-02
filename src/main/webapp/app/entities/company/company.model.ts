import { BaseEntity } from './../../shared';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public activity?: string,
        public phone?: string,
        public email?: string,
        public addressId?: number,
        public shareProfils?: BaseEntity[],
        public recrutementProfils?: BaseEntity[],
    ) {
    }
}
