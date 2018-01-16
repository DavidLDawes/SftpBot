import { BaseEntity } from './../../shared';

export class Sftproot implements BaseEntity {
    constructor(
        public id?: number,
        public incomingDirectory?: string,
        public outgoingDirectory?: string,
        public errorDirectory?: string,
        public sftpTestCases?: BaseEntity[],
    ) {
    }
}
