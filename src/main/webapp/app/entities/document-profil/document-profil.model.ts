import { BaseEntity } from './../../shared';

export class DocumentProfil implements BaseEntity {
    constructor(
        public id?: number,
        public documentFileContentType?: string,
        public documentFile?: any,
        public shareProfilId?: number,
        public recrutementProfilId?: number,
    ) {
    }
}
