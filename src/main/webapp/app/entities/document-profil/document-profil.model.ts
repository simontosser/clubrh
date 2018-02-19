import { BaseEntity } from './../../shared';

export class DocumentProfil implements BaseEntity {
    constructor(
        public id?: number,
        public documentFileContentType?: string,
        public documentFile?: any,
        public documentFileName?: string,
        public shareProfilId?: number,
        public recrutementProfilId?: number,
    ) {
    }
}
