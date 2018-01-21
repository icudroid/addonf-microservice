/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdvertiserBoTestModule } from '../../../test.module';
import { FileAttachementComponent } from '../../../../../../main/webapp/app/entities/file-attachement/file-attachement.component';
import { FileAttachementService } from '../../../../../../main/webapp/app/entities/file-attachement/file-attachement.service';
import { FileAttachement } from '../../../../../../main/webapp/app/entities/file-attachement/file-attachement.model';

describe('Component Tests', () => {

    describe('FileAttachement Management Component', () => {
        let comp: FileAttachementComponent;
        let fixture: ComponentFixture<FileAttachementComponent>;
        let service: FileAttachementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [FileAttachementComponent],
                providers: [
                    FileAttachementService
                ]
            })
            .overrideTemplate(FileAttachementComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FileAttachementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FileAttachementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new FileAttachement(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.fileAttachements[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
