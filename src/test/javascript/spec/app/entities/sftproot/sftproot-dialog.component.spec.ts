/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SftpbotTestModule } from '../../../test.module';
import { SftprootDialogComponent } from '../../../../../../main/webapp/app/entities/sftproot/sftproot-dialog.component';
import { SftprootService } from '../../../../../../main/webapp/app/entities/sftproot/sftproot.service';
import { Sftproot } from '../../../../../../main/webapp/app/entities/sftproot/sftproot.model';

describe('Component Tests', () => {

    describe('Sftproot Management Dialog Component', () => {
        let comp: SftprootDialogComponent;
        let fixture: ComponentFixture<SftprootDialogComponent>;
        let service: SftprootService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SftpbotTestModule],
                declarations: [SftprootDialogComponent],
                providers: [
                    SftprootService
                ]
            })
            .overrideTemplate(SftprootDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SftprootDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SftprootService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Sftproot(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.sftproot = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sftprootListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Sftproot();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.sftproot = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sftprootListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
