/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { SftpbotTestModule } from '../../../test.module';
import { SftprootDetailComponent } from '../../../../../../main/webapp/app/entities/sftproot/sftproot-detail.component';
import { SftprootService } from '../../../../../../main/webapp/app/entities/sftproot/sftproot.service';
import { Sftproot } from '../../../../../../main/webapp/app/entities/sftproot/sftproot.model';

describe('Component Tests', () => {

    describe('Sftproot Management Detail Component', () => {
        let comp: SftprootDetailComponent;
        let fixture: ComponentFixture<SftprootDetailComponent>;
        let service: SftprootService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SftpbotTestModule],
                declarations: [SftprootDetailComponent],
                providers: [
                    SftprootService
                ]
            })
            .overrideTemplate(SftprootDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SftprootDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SftprootService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Sftproot(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sftproot).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
