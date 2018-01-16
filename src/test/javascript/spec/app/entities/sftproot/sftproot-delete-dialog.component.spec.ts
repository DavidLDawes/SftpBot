/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SftpbotTestModule } from '../../../test.module';
import { SftprootDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/sftproot/sftproot-delete-dialog.component';
import { SftprootService } from '../../../../../../main/webapp/app/entities/sftproot/sftproot.service';

describe('Component Tests', () => {

    describe('Sftproot Management Delete Component', () => {
        let comp: SftprootDeleteDialogComponent;
        let fixture: ComponentFixture<SftprootDeleteDialogComponent>;
        let service: SftprootService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SftpbotTestModule],
                declarations: [SftprootDeleteDialogComponent],
                providers: [
                    SftprootService
                ]
            })
            .overrideTemplate(SftprootDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SftprootDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SftprootService);
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
