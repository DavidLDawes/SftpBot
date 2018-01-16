/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SftpbotTestModule } from '../../../test.module';
import { SftpTestCaseDialogComponent } from '../../../../../../main/webapp/app/entities/sftp-test-case/sftp-test-case-dialog.component';
import { SftpTestCaseService } from '../../../../../../main/webapp/app/entities/sftp-test-case/sftp-test-case.service';
import { SftpTestCase } from '../../../../../../main/webapp/app/entities/sftp-test-case/sftp-test-case.model';
import { SftprootService } from '../../../../../../main/webapp/app/entities/sftproot';

describe('Component Tests', () => {

    describe('SftpTestCase Management Dialog Component', () => {
        let comp: SftpTestCaseDialogComponent;
        let fixture: ComponentFixture<SftpTestCaseDialogComponent>;
        let service: SftpTestCaseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SftpbotTestModule],
                declarations: [SftpTestCaseDialogComponent],
                providers: [
                    SftprootService,
                    SftpTestCaseService
                ]
            })
            .overrideTemplate(SftpTestCaseDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SftpTestCaseDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SftpTestCaseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SftpTestCase(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.sftpTestCase = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sftpTestCaseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SftpTestCase();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.sftpTestCase = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sftpTestCaseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
