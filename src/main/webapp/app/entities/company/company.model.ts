import { Address } from '../address/address.model';
import { BaseEntity } from './../../shared';

export class Company implements BaseEntity {

    public id: number;
    public name: string;
    public activity: string;
    public phone: string;
    public email: string;
    public address = new Address();
    public shareProfils: BaseEntity[];
    public recrutementProfils: BaseEntity[];

    constructor(
        id?: number,
        name?: string,
        activity?: string,
        phone?: string,
        email?: string,
        address?: Address,
        shareProfils?: BaseEntity[],
        recrutementProfils?: BaseEntity[],
    ) {
        this.id = id;
        this.name = name;
        this.activity = activity;
        this.phone = phone;
        this.email = email;
        this.address = (address) ? address : new Address();
        this.shareProfils = shareProfils;
        this.recrutementProfils = recrutementProfils;

    }
}
