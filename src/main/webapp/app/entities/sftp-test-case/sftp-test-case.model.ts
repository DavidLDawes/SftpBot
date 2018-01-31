import { BaseEntity } from './../../shared';

export class SftpTestCase implements BaseEntity {
    constructor(
        public id?: number,
        public incomingFileName?: string,
        public resultFileName?: string,
        public errorFileName?: string,
        public fileContents?: string,
        public delay?: number,
        public sftprootId?: number,
    ) {
    }
}
