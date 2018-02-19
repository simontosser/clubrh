import { DocumentProfil } from '../document-profil/document-profil.model';
import { BaseEntity } from './../../shared';

export class RecrutementProfil implements BaseEntity {
    constructor(
        public id?: number,
        public jobTitle?: string,
        public context?: string,
        public finalyObject?: string,
        public mainActivity?: string,
        public mainSkill?: string,
        public knownledge?: string,
        public deadline?: any,
        public consideration?: string,
        public remind?: string,
        public comment?: string,
        public documentProfils: DocumentProfil[] = [],
        public companyId?: number,
    ) {
    }
}
