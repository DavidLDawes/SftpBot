/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { SftpbotTestModule } from '../../../test.module';
import { SftpTestCaseDetailComponent } from '../../../../../../main/webapp/app/entities/sftp-test-case/sftp-test-case-detail.component';
import { SftpTestCaseService } from '../../../../../../main/webapp/app/entities/sftp-test-case/sftp-test-case.service';
import { SftpTestCase } from '../../../../../../main/webapp/app/entities/sftp-test-case/sftp-test-case.model';

describe('Component Tests', () => {

    describe('SftpTestCase Management Detail Component', () => {
        let comp: SftpTestCaseDetailComponent;
        let fixture: ComponentFixture<SftpTestCaseDetailComponent>;
        let service: SftpTestCaseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SftpbotTestModule],
                declarations: [SftpTestCaseDetailComponent],
                providers: [
                    SftpTestCaseService
                ]
            })
            .overrideTemplate(SftpTestCaseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SftpTestCaseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SftpTestCaseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SftpTestCase(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sftpTestCase).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
