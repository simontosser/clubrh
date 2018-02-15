import { DocumentProfil } from '../document-profil/document-profil.model';
import { BaseEntity } from './../../shared';

export class ShareProfil implements BaseEntity {
    constructor(
        public id?: number,
        public targetJob?: string,
        public skillShield?: string,
        public mainActivity?: string,
        public carryActivity?: string,
        public skillExpends?: string,
        public context?: string,
        public collaboType?: string,
        public consideration?: string,
        public comment?: string,
        public documentProfils?: DocumentProfil[],
        public companyId?: number,
    ) {
    }
}
