/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SftpbotTestModule } from '../../../test.module';
import { SftpTestCaseDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/sftp-test-case/sftp-test-case-delete-dialog.component';
import { SftpTestCaseService } from '../../../../../../main/webapp/app/entities/sftp-test-case/sftp-test-case.service';

describe('Component Tests', () => {

    describe('SftpTestCase Management Delete Component', () => {
        let comp: SftpTestCaseDeleteDialogComponent;
        let fixture: ComponentFixture<SftpTestCaseDeleteDialogComponent>;
        let service: SftpTestCaseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SftpbotTestModule],
                declarations: [SftpTestCaseDeleteDialogComponent],
                providers: [
                    SftpTestCaseService
                ]
            })
            .overrideTemplate(SftpTestCaseDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SftpTestCaseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SftpTestCaseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
